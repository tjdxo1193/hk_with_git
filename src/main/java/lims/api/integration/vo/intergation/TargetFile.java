package lims.api.integration.vo.intergation;

import lims.api.util.FileUtil;
import org.apache.commons.lang3.StringUtils;

public class TargetFile {
    private String name;
    private String extension;

    public TargetFile(String name) {
        this.name = name;
    }

    public TargetFile(String name, String extension) {
        this.name = name;
        this.extension = extension;
    }

    public String getFullName() {
        if (StringUtils.isEmpty(extension)) {
            return name;
        }
        return FileUtil.concat(name, extension);
    }
}