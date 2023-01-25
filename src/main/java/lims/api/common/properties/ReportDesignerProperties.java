package lims.api.common.properties;

import lims.api.common.properties.domain.ServerProperty;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.io.File;
import java.util.Optional;

@Getter
@ConstructorBinding
@ConfigurationProperties(prefix = "report-designer")
public class ReportDesignerProperties {
    private final String serviceName;
    private final String repositoryPath;
    private final String user;
    private final String password;
    private final String temporaryDirForExport;
    private final ServerProperty dataServer;
    private final ServerProperty reportingServer;

    public ReportDesignerProperties(String serviceName, String repositoryPath, String user, String password, String temporaryDirForExport, ServerProperty dataServer, ServerProperty reportingServer) {
        this.serviceName = serviceName;
        this.repositoryPath = repositoryPath;
        this.user = user;
        this.password = password;
        this.temporaryDirForExport = getTemporaryDirPath(temporaryDirForExport);
        this.dataServer = dataServer;
        this.reportingServer = reportingServer;
    }

    private String getTemporaryDirPath(String path) {
        return Optional.ofNullable(path).orElse("C:" + File.separator + "report-designer-temp");
    }
}