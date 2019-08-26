package indi.aby.docm.core.contract;

import indi.aby.docm.api.contract.IDownloadServiceApi;
import indi.aby.docm.api.contract.AttachmentVO;
import indi.rui.common.base.field.IFieldId;
import indi.rui.common.base.util.DateUtil;
import indi.rui.common.base.util.FileUtil;
import indi.rui.common.base.util.ZipUtil;
import indi.rui.common.web.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static indi.aby.docm.api.util.ErrorCode.CREATING_FILE_DIRECTORY_FAILED;

@Slf4j
@Service
public class DownloadService implements IDownloadServiceApi, InitializingBean {
    @Value("${docm.filePath:./tmp/upload}")
    private String filePath;

    @Value("${docm.zipTmpPath:./tmp/zip}")
    private String zipTmpPath;

    @Autowired
    private DocmMapper docmMapper;

    @Autowired
    private AttachmentMapper attachmentMapper;

    @Override
    public void afterPropertiesSet() throws Exception {
        File file = new File(filePath);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                throw new BizException(CREATING_FILE_DIRECTORY_FAILED);
            }
        }
        File zip = new File(zipTmpPath);
        if (!zip.exists()) {
            if (!zip.mkdirs()) {
                throw new RuntimeException("创建暂存目录失败");
            }
        }
    }

    @Override
    public List<AttachmentVO> upload(List<MultipartFile> files) {
        List<AttachmentVO> attachments = new ArrayList<>();
        if (files != null) {
            attachments = files.stream().map(multipartFile -> {
                AttachmentVO attachmentVO = new AttachmentVO(filePath + File.separator +
                        DateUtil.now("yyyyMMddHHmmss"), multipartFile.getOriginalFilename());
                try {
                    FileUtil.save(multipartFile.getInputStream(), attachmentVO.getDocPath(), attachmentVO.getDocName());
                } catch (IOException e) {
                    log.error(e.getMessage());
                    throw new RuntimeException("文件保存失败");
                }
                return attachmentVO;
            }).collect(Collectors.toList());
        }
        return attachments;
    }

    @Override
    public ResponseEntity download(IFieldId fieldId) {
        String filename = buildZipFile(fieldId); // 把多个文件打成zip压缩包
        InputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(new File(zipTmpPath, filename)));
            byte[] buf = new byte[in.available()];
            in.read(buf);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" +
                    new String(filename.getBytes("utf-8"), "iso8859-1"));
            return new ResponseEntity(buf, httpHeaders, HttpStatus.OK);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException("下载异常");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    throw new RuntimeException("下载异常");
                }
            }
        }
    }

    private String buildZipFile(IFieldId fieldId) {
        try {
            DocmEntity docm = docmMapper.findById(fieldId);
            String filename = docm.getProjectName().concat(".zip");
            List<AttachmentEntity> attachments = attachmentMapper.findById(docm);
            if (attachments != null) {
                ZipUtil.zipFile(attachments.stream()
                        .map(attachment -> new File(attachment.getDocPath(), attachment.getDocName()))
                        .collect(Collectors.toList()), new File(zipTmpPath, filename));
            }
            return filename;
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException("压缩文件异常");
        }
    }

    @Override
    public List<String> clean() {
        List<String> cleanedFiles = new ArrayList<>();
        File pathFile = new File(filePath);
        if (pathFile.exists()) {
            File[] dirs = pathFile.listFiles();
            if (dirs != null && dirs.length > 0) {
                cleanedFiles = Arrays.stream(dirs).filter(file -> {
                    String docPath = filePath + File.separator + file.getName();
                    String[] subFiles = file.list();
                    if (subFiles != null && subFiles.length > 0) {
                        String docName = subFiles[0];   // 一个目录只存一个文件
                        AttachmentEntity attachmentEntity = attachmentMapper.findByPath(docPath, docName);
                        return attachmentEntity == null;
                    }
                    return true;
                }).map(file -> {
                    String deleteFilePath = file.getAbsolutePath();
                    FileUtil.deleteDir(deleteFilePath);
                    return deleteFilePath;
                }).collect(Collectors.toList());
            }
        }
        return cleanedFiles;
    }
}
