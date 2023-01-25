package lims.api.config;

import lims.api.common.service.impl.AuditRecorder;
import lims.api.common.service.impl.TransactionAuditConverter;
import lims.api.common.vo.AuditRecordData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import spring.audit.domain.AuditTrail;
import spring.audit.event.AuditEventListener;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class AuditTrailListener implements AuditEventListener {

    private final TransactionAuditConverter auditConverter;
    private final AuditRecorder transactionAuditRecorder;

    @Override
    public void beforeCommit(List<AuditTrail> auditTrails) {
        List<AuditRecordData> detailParams = auditTrails.stream().map(auditConverter::convert).flatMap(List::stream).collect(Collectors.toList());
        transactionAuditRecorder.record(detailParams);
    }
}