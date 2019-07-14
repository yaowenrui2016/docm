package indi.aby.docm.api;

import indi.aby.docm.api.dto.AttachmentVO;
import indi.rui.common.base.field.IFieldId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IDownloadServiceApi {
    List<AttachmentVO> upload(List<MultipartFile> files);
    ResponseEntity download(IFieldId fieldId);
    void remove(AttachmentVO attachmentVO);
}
