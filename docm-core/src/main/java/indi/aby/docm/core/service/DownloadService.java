package indi.aby.docm.core.service;

import indi.aby.docm.api.IDownloadServiceApi;
import indi.aby.docm.api.dto.AttachmentVO;
import indi.rui.common.base.util.DateUtil;
import indi.rui.common.base.util.FileUtil;
import indi.rui.common.base.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DownloadService implements IDownloadServiceApi {
    @Value("${docm.filePath:./tmp/upload}")
    private String filePath;

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
                    throw new RuntimeException("文件保存失败");
                }
                return attachmentVO;
            }).collect(Collectors.toList());
        }
        return attachments;
    }

    @Override
    public void remove(AttachmentVO attachmentVO) {
        FileUtil.remove(attachmentVO.getDocPath(), attachmentVO.getDocName());
    }
}
