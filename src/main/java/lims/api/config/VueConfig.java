package lims.api.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;

@Slf4j
@Configuration
public class VueConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**/*")
                .addResourceLocations("classpath:/static/")
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
                        Resource requestedResource = location.createRelative(resourcePath);
                        log.debug("@@@@@@@@@@@@@@@@@@@@@@");
                        log.debug(location.toString());
                        log.debug(resourcePath);
                        if (requestedResource.exists()) {
                            log.debug(requestedResource.getURI().toString());
                            log.debug(requestedResource.getFilename());
                            log.debug(requestedResource.getDescription());
                            log.debug(String.valueOf(requestedResource.isFile()));
                        }
                        if (requestedResource.exists() && requestedResource.isReadable()) {
                            return requestedResource;
                        }
                        log.debug("#####################");
                        log.debug("Load index.html");
                        return new ClassPathResource("/static/index.html");
                    }
                });
    }

}