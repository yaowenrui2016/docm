package indi.aby.docm.core.contract;

import indi.aby.docm.api.contract.IDownloadServiceApi;
import indi.aby.docm.api.permission.annotation.Permission;
import indi.rui.common.base.dto.IdVO;
import indi.rui.common.base.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("doc")
public class DownloadController {
    @Autowired
    private IDownloadServiceApi downloadServiceApi;

    @PostMapping
//    @Permission(id = "DOCM_UPLOAD_OPER", name = "项目合同_附件上传权限", validator = "simplePermValidator", module = "项目合同", desc = "拥有该权限才可以上传合同的附件")
    public Response<?> upload(@RequestBody List<MultipartFile> files) {
        return Response.ok(downloadServiceApi.upload(files));
    }

    @GetMapping
    @Permission(id = "DOCM_DOWNLOAD_OPER", name = "项目合同_附件下载权限", validator = "simplePermValidator", module = "项目合同", desc = "拥有该权限才可以下载合同的附件")
    public ResponseEntity download(@ModelAttribute IdVO idVO, HttpServletRequest request) {
        return downloadServiceApi.download(idVO, request);
    }
}
