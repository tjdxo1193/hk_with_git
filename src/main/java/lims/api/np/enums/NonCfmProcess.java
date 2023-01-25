package lims.api.np.enums;

import lombok.Getter;

@Getter
public enum NonCfmProcess {
    UNSUITABLE_NOTICE_WRITE("S0250100"),
    UNSUITABLE_NOTICE_REJECT("S0250110"),
    UNSUITABLE_NOTICE_REQUEST("S0250200"),
    UNSUITABLE_NOTICE_APPROVE("S0250300"),
    RELAPSE_PREV_REQUEST("S0250400"),
    RELAPSE_PREV_REJECT("S0250410"),
    RELAPSE_PREV_APPROVE("S0250500");

    private final String processCode;

    NonCfmProcess(String code) {
        processCode = code;
    }

    public boolean equals(NonCfmProcess process) {
        return this == process;
    }
}
