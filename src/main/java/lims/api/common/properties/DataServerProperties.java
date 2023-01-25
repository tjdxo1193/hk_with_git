package lims.api.common.properties;

import lims.api.common.properties.domain.ServerProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "report-designer.data-server")
public class DataServerProperties extends ServerProperty {
    public DataServerProperties(String url) {
        super(url);
    }
}