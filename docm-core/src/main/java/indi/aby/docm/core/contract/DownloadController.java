package indi.aby.docm.core.contract;

import indi.aby.docm.api.IDownloadServiceApi;
import indi.aby.docm.api.permission.Permission;
import indi.rui.common.base.dto.IdVO;
import indi.rui.common.base.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("doc")
public class DownloadController {
    @Autowired
    private IDownloadServiceApi downloadServiceApi;

    @PostMapping
    public Response<?> upload(@RequestBody List<MultipartFile> files) {
        return Response.ok(downloadServiceApi.upload(files));
    }

    @GetMapping
    @Permission(id = "DOCM_DOWNLOAD_OPER", name = "项目合同_附件下载权限", module = "项目合同", desc = "项目合同的附件下载操作")
    public ResponseEntity download(@ModelAttribute IdVO idVO) {
        return downloadServiceApi.download(idVO);
    }
}
