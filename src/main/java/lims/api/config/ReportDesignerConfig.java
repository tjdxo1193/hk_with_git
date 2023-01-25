package lims.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ReportDesignerConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/mrd/*")
                .addResourceLocations("classpath:/mrd/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/mrd").allowedOrigins("*");
    }
}