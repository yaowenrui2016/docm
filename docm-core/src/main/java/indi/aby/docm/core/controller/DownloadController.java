package indi.aby.docm.core.controller;

import indi.aby.docm.api.IDownloadServiceApi;
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
    public ResponseEntity download(@ModelAttribute IdVO idVO) {
        return downloadServiceApi.download(idVO);
    }
}
