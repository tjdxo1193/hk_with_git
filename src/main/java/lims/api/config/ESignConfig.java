package lims.api.config;

import lims.api.common.interceptor.spring.ESignInterceptor;
import lims.api.common.service.impl.AuditESign;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class ESignConfig implements WebMvcConfigurer {

    private final AuditESign auditESign;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ESignInterceptor(auditESign)).addPathPatterns("/**");
    }

}