package indi.aby.docm.core.contract;

import indi.rui.common.base.field.IFieldId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 附件上传、下载api
 */
public interface IDownloadApi {
    List<AttachmentVO> upload(List<MultipartFile> files);
    ResponseEntity zipAllDownload(IFieldId fieldId, HttpServletRequest request);
    ResponseEntity singleDownload(IFieldId fieldId, HttpServletRequest request);
    List<String> clean();
    List<InvalidAttachInfo> checkInvalidAttach();
    void deleteInvalidAttach(List<InvalidAttachInfo> attachInfos);
}
