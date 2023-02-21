package lims.api.sd.enums;

import lombok.Getter;

@Getter
public enum StandardMaterialProcess {
    SAVE("S0010100"),
    APPROVE_RETURN("S0010110"),
    APPROVE_REQUEST("S0010200"),
    APPROVE_COMPLETE("S0010300"),
    WAREHOUSING("S0020000"),
    STOCK("S0020100"),
    OPEN("S0020200"),
    DISPOSAL_REQUEST_IN_STOCK("S0020190"),
    DISPOSAL_REQUEST_IN_OPEN("S0020290"),
    DISPOSAL("S0020300");

    private final String processCode;

    StandardMaterialProcess(String code) {
        processCode = code;
    }

    public boolean equals(StandardMaterialProcess process) {
        return this == process;
    }

}
