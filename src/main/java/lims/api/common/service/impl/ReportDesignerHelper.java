package lims.api.common.service.impl;

import lims.api.common.properties.ReportDesignerProperties;
import lims.api.integration.enums.FileType;
import lims.api.integration.vo.intergation.TempFile;
import lims.api.util.FileUtil;
import lims.api.util.StringUtil;
import lims.api.util.ThreadUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import m2soft.ers.invoker.InvokerException;
import m2soft.ers.invoker.http.ReportingServerInvoker;
import org.springframework.stereotype.Service;

import java.io.File;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportDesignerHelper {

    private final ReportDesignerProperties properties;

    public TempFile toTempFile(String mrdName, String param, String targetFileName, FileType type) {
        return toTempFile(mrdName, param, FileUtil.concat(targetFileName, type.getExtension()));
    }

    public TempFile toTempFile(String mrdName, String param, String targetFileName) {
        try {
            String targetFilePath = downloadToServerAndGetCreatedFilePath(mrdName, createMrdParam(param), targetFileName);
            File file = new File(targetFilePath);
            return TempFile.builder().file(file).build();
        } catch (InvokerException e) {
            log.error(
                    "[{}] Failed convert MRD to File. mrd name: {}, mrd parameter: {}, target file name: {}",
                    ThreadUtil.getCurrentMethodName(),
                    mrdName,
                    param,
                    targetFileName
            );
            throw new RuntimeException(e);
        } catch (Exception e) {
            log.error(
                    "[{}] Failed convert MRD to File. mrd name: {}, mrd parameter: {}, target file name: {}",
                    ThreadUtil.getCurrentMethodName(),
                    mrdName,
                    param,
                    targetFileName
            );
            throw e;
        }
    }

    private String downloadToServerAndGetCreatedFilePath(String mrdName, String param, String targetFileName) throws InvokerException {
        String mrdPath = getMrdPath(mrdName);
        String targetFileExtension = FileUtil.getExtension(targetFileName);
        String currentDirName = StringUtil.generateUUID();

        String temporaryDirPath = getTemporaryDirPath();
        createDirectory(temporaryDirPath);

        String targetFilePath = FileUtil.concat(temporaryDirPath, currentDirName + targetFileName);

        ReportingServerInvoker invoker = new ReportingServerInvoker(getReportingServerUrl());
        invoker.setCharacterEncoding("utf-8");
        invoker.setReconnectionCount(3);
        invoker.setConnectTimeout(10);
        invoker.setReadTimeout(600);

        invoker.addParameter("opcode", "500");
        invoker.addParameter("mrd_path", mrdPath);
        invoker.addParameter("mrd_param", param);
        invoker.addParameter("export_type", targetFileExtension);
        invoker.addParameter("export_name", targetFileName);
        invoker.addParameter("protocol", "file");

        log.info("Converting MRD file to File. mrd file: {}, target file: {}", mrdPath, targetFilePath);
        invoker.invoke(targetFilePath);

        return targetFilePath;
    }

    private void createDirectory(String path) {
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    private String getMrdPath(String name) {
        String repositoryPath = properties.getRepositoryPath();
        String separator = "/";
        if (!repositoryPath.endsWith(separator)) {
            repositoryPath += separator;
        }
        return repositoryPath + name;
    }

    private String getTemporaryDirPath() {
        return properties.getTemporaryDirForExport();
    }

    private String getReportingServerUrl() {
        return properties.getReportingServer().getUrl();
    }

    private String createMrdParam(String param) {
        return new StringBuilder()
                .append("/rcontype [").append("Data Server")
                .append("] /rf [").append(properties.getDataServer().getUrl())
                .append("] /rsn [").append(properties.getServiceName())
                .append("] /rui [").append(properties.getUser())
                .append("] /rpw [").append(properties.getPassword())
                .append("] ").append(param)
                .toString();
    }

}