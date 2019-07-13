package indi.aby.docm.core.controller;

import indi.aby.docm.api.IDownloadServiceApi;
import indi.aby.docm.api.dto.AttachmentVO;
import indi.rui.common.base.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
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

    @DeleteMapping
    public Response<?> remove(@ModelAttribute AttachmentVO attachmentVO) {
        downloadServiceApi.remove(attachmentVO);
        return Response.ok();
    }
}
