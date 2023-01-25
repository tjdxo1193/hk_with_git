package lims.api.in.enums;

import lombok.Getter;

@Getter
public enum EquipmentHistoryProcess {
    TEMP_SAVE("S0190100"),
    APPROVE_REQUEST("S0190200"),
    APPROVE_RETURN("S0190210"),
    APPROVE_COMPLETE("S0190300"),
    PMS_REGISTER("S0190900");

    private final String processCode;

    EquipmentHistoryProcess(String code) {
        processCode = code;
    }

    public boolean equals(EquipmentHistoryProcess process) {
        return this == process;
    }

}
