package indi.aby.docm.api.contract;

import indi.aby.docm.api.contract.AttachmentVO;
import indi.rui.common.base.field.IFieldId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IDownloadServiceApi {
    List<AttachmentVO> upload(List<MultipartFile> files);
    ResponseEntity download(IFieldId fieldId);
    List<String> clean();
}
