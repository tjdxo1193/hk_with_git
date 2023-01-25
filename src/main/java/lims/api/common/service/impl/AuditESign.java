package lims.api.common.service.impl;

import lims.api.common.vo.ESignVO;
import org.springframework.stereotype.Service;
import spring.audit.event.AuditTransactionManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuditESign {

    public static final String E_SIGN_RESOURCE_NAME = "esignResource";

    public ESignVO getESign() {
        return Optional.ofNullable(getESignAtResource()).orElse(new ESignVO());
    }

    private ESignVO getESignAtResource() {
        if (!hasESignAtAuditTransaction()) {
            return null;
        }
        return (ESignVO) getResource().get(E_SIGN_RESOURCE_NAME);
    }

    public boolean hasESignAtAuditTransaction() {
        if (!AuditTransactionManager.hasCustomTransactionResource()) {
            return false;
        }
        Map<String, Object> resource = getResource();
        return resource.containsKey(E_SIGN_RESOURCE_NAME);
    }

    private Map<String, Object> getResource() {
        return AuditTransactionManager.getCustomResource();
    }

    public void setResourceToAuditTransaction(ESignVO eSign) {
        Map<String, Object> resource = getAuditResource();
        resource.put(AuditESign.E_SIGN_RESOURCE_NAME, eSign);
        AuditTransactionManager.setCustomResource(resource);
    }

    private Map<String, Object> getAuditResource() {
        if (AuditTransactionManager.hasCustomTransactionResource()) {
            return AuditTransactionManager.getCustomResource();
        }
        return new HashMap<>();
    }

}