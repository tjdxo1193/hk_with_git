package lims.api.common.service.impl;

import lims.api.common.domain.ESignInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ESignHelper {

    private final AuditESign auditESign;

    public ESignInfo getESignInfo() {
        return ESignInfo.of(auditESign.getESign());
    }

    public boolean hasESignInCurrentTransaction() {
        return auditESign.hasESignAtAuditTransaction();
    }

}