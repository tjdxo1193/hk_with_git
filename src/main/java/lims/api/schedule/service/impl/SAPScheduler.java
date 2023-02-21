package lims.api.schedule.service.impl;

import lims.api.integration.service.SAPService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SAPScheduler {

    private final SAPService sapService;

    @Scheduled(cron = "0 0 22 1 * *")
    public void testPerformance() {

        // TODO 연계 데이터 생성

        // sapService.publishTestPerformance();
    }

}