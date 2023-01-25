package lims.api.integration.vo.intergation;

import lombok.AccessLevel;
import lombok.Getter;

import java.util.Optional;

@Getter
public class ConvertMrd {
    private final String name;
    private final String parameter;
    @Getter(AccessLevel.NONE)
    private final TargetFile targetFile;

    public ConvertMrd(String name, String parameter, TargetFile targetFile) {
        this.name = getMrdName(name);
        this.parameter = parameter;
        this.targetFile = Optional.ofNullable(targetFile).orElseThrow(() -> new NullPointerException("'TargetFile' type argument is required."));
    }

    public String getTargetFullName() {
        return targetFile.getFullName();
    }

    private String getMrdName(String name) {
        String mrdName = Optional.ofNullable(name).orElseThrow(() -> new NullPointerException("MRD name is required."));
        String suffix = ".mrd";
        return mrdName.endsWith(suffix) ? mrdName : mrdName + suffix;
    }
}