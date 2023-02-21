package lims.api.common.properties;

import lims.api.common.properties.domain.ServerProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "report-designer.reporting-server")
public class ReportingServerProperties extends ServerProperty {
    public ReportingServerProperties(String url) {
        super(url);
    }
}