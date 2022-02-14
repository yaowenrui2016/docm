package indi.aby.docm.core.contract;

import indi.rui.common.base.dto.QueryRequest;
import indi.rui.common.base.field.IFieldId;
import indi.rui.common.base.util.DateUtil;
import indi.rui.common.base.util.FileUtil;
import indi.rui.common.base.util.MD5Util;
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
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static indi.aby.docm.util.ErrorCode.*;

/**
 * 附件上传、下载service
 */
@Slf4j
@Service
public class Download implements IDownloadApi, InitializingBean {

    @Value("${docm.filePath:./tmp/upload}")
    private String filePath;
    @Value("${docm.zipTmpPath:./tmp/zip}")
    private String zipTmpPath;

    @Autowired
    private ContractMapper contractMapper;
    @Autowired
    private AttachmentMapper attachmentMapper;
    @Autowired
    private PlatformTransactionManager transactionManager;

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
        // 可以支持一次传多个文件
        List<AttachmentVO> attachments = new ArrayList<>();
        if (files != null) {
            attachments = files.stream().map(multipartFile -> {
                AttachmentVO attachmentVO = new AttachmentVO(File.separator +
                        DateUtil.now("yyyyMMddHHmmss"), multipartFile.getOriginalFilename());
                try {
                    attachmentVO.setType(multipartFile.getContentType());
                    attachmentVO.setSize(multipartFile.getSize());
                    attachmentVO.setMd5(MD5Util.encrypt(multipartFile.getBytes()));
                    FileUtil.save(multipartFile.getInputStream(), filePath + attachmentVO.getDocPath(), attachmentVO.getDocName());
                } catch (IOException e) {
                    log.error(e.getMessage());
                    throw new BizException(FILE_SAVING_EXCEPTION);
                }
                return attachmentVO;
            }).collect(Collectors.toList());
        }
        return attachments;
    }

    private ResponseEntity download(String path, String filename, HttpServletRequest request) {
        InputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(new File(path, filename)));
            byte[] buf = new byte[in.available()];
            in.read(buf);
            HttpHeaders httpHeaders = new HttpHeaders();
            String encodedFilename = URLEncoder.encode(filename, "utf-8");
            String dispositionContent = "attachment;filename=" + encodedFilename;
            if (request.getHeader("User-Agent").toLowerCase().contains("firefox")) {
                dispositionContent = "attachment;filename*=utf-8''" + encodedFilename;
            }
            httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, dispositionContent);
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

    @Override
    public ResponseEntity singleDownload(IFieldId fieldId, HttpServletRequest request) {
        AttachmentEntity entity = attachmentMapper.findById(fieldId);
        return download(filePath + entity.getDocPath(), entity.getDocName(), request);
    }

    @Override
    public ResponseEntity zipAllDownload(IFieldId fieldId, HttpServletRequest request) {
        ContractEntity docm = contractMapper.findById(fieldId);
        if (docm == null) {
            log.warn("合同不存在");
            return null;
        }
        String filename = docm.getProjectName().concat(".zip");
        List<AttachmentEntity> attachments = attachmentMapper.findByDocmId(docm);
        if (CollectionUtils.isEmpty(attachments)) {
            log.warn("该合同没有附件");
            return null;
        }
        try {
            ZipUtil.zipFile(attachments.stream()
                    .map(attachment -> new File(filePath + attachment.getDocPath(), attachment.getDocName()))
                    .collect(Collectors.toList()), new File(zipTmpPath, filename));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new BizException(FILE_RESOURCE_EXCEPTION);
        }
        return download(zipTmpPath, filename, request);
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

    @Override
    public List<InvalidAttachInfo> checkInvalidAttach() {
        List<InvalidAttachInfo> invalidAttachs = new ArrayList<>();
        QueryRequest request = new QueryRequest();
        request.setPageSize(3000);
        List<AttachmentEntity> attachments = attachmentMapper.findAll(request);
        for (AttachmentEntity entity : attachments) {
            File file = new File(filePath + entity.getDocPath(), entity.getDocName());
            if (!file.exists()) {
                InvalidAttachInfo attachInfo = new InvalidAttachInfo();
                attachInfo.setPath(file.getAbsolutePath());
                attachInfo.setAttachId(entity.getId());
                invalidAttachs.add(attachInfo);
            }
        }
        return invalidAttachs;
    }


    @Override
    public void deleteInvalidAttach(List<InvalidAttachInfo> attachInfos) {
        if (CollectionUtils.isEmpty(attachInfos)) {
            return;
        }
        for (InvalidAttachInfo attachInfo : attachInfos) {
            attachmentMapper.delete(() -> attachInfo.getAttachId());
        }
    }
}
