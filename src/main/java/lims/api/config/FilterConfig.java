package lims.api.config;

import lims.api.common.filter.HttpMethodGuardFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<HttpMethodGuardFilter> httpMethodGuardFilter() {
        FilterRegistrationBean<HttpMethodGuardFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new HttpMethodGuardFilter());
        bean.addUrlPatterns("/**");
        bean.setOrder(1);
        return bean;
    }

}