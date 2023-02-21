package lims.api.common.resolver;

import lims.api.auth.annotation.ESign;
import lims.api.common.domain.ESignInfo;
import lims.api.common.service.impl.AuditESign;
import lims.api.common.service.impl.ESignHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class ESignArgumentResolver implements HandlerMethodArgumentResolver {

    private final ESignHelper eSignHelper;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(ESign.class);
    }

    @Override
    public ESignInfo resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        return eSignHelper.getESignInfo();
    }
}