package lims.api.config;

import com.fasterxml.jackson.databind.module.SimpleModule;
import lims.api.config.xss.XSSStringConverter;
import lims.api.config.xss.XSSStringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class XSSConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new XSSStringConverter());
    }

    @Bean
    public SimpleModule xssStringModule() {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(String.class, new XSSStringDeserializer());
        return simpleModule;
    }

}