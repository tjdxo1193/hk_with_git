package lims.api.common.interceptor.spring;

import lims.api.auth.service.impl.HttpHelper;
import lims.api.common.service.impl.AuditESign;
import lims.api.common.vo.ESignVO;
import org.springframework.web.servlet.HandlerInterceptor;
import spring.audit.event.AuditTransactionManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class ESignInterceptor implements HandlerInterceptor {

    private final AuditESign auditESign;

    public ESignInterceptor(AuditESign auditESign) {
        this.auditESign = auditESign;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (HttpHelper.isExistsInRequestHeader(HttpHelper.E_SIGN_HEADER_NAME)) {
            ESignVO eSign = HttpHelper.getESignAtRequestHeader();
            eSign.sign();
            auditESign.setResourceToAuditTransaction(eSign);
        }
        return true;
    }
}