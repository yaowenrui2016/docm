package indi.aby.docm.api.contract;

import indi.rui.common.base.field.IFieldId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IDownloadServiceApi {
    List<AttachmentVO> upload(List<MultipartFile> files);
    ResponseEntity download(IFieldId fieldId, HttpServletRequest request);
    List<String> clean();
}
