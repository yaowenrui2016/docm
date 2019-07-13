package indi.aby.docm.api;

import indi.aby.docm.api.dto.AttachmentVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IDownloadServiceApi {
    List<AttachmentVO> upload(List<MultipartFile> files);
    void remove(AttachmentVO attachmentVO);
}
