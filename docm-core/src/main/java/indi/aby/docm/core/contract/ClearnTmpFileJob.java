package indi.aby.docm.core.contract;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ClearnTmpFileJob {
    @Autowired
    private DownloadService downloadService;

    @Scheduled(cron = "0 0 2 * * ?")
    public void run() {
        log.info("清理文件：{}", Arrays.toString(downloadService.clean().toArray()));
    }
}
