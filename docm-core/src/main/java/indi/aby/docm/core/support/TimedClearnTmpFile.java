package indi.aby.docm.core.support;

import indi.aby.docm.core.dao.DocmMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TimedClearnTmpFile {
    @Autowired
    private DocmMapper docmMapper;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void run() {
        log.info("走你...");
    }
}
