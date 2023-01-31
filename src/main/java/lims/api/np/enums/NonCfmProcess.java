package lims.api.np.enums;

import lombok.Getter;

@Getter
public enum NonCfmProcess {
    NON_CFM_REPORT_WRITE("S0250100"),
    NON_CFM_REPORT_REJECT("S0250110"),
    NON_CFM_REPORT_REQUEST("S0250200"),
    NON_CFM_REPORT_APPROVE("S0250300"),
    PRV_RCR_REPORT_REQUEST("S0250400"),
    PRV_RCR_REPORT_REJECT("S0250410"),
    PRV_RCR_REPORT_APPROVE("S0250500");

    private final String processCode;

    NonCfmProcess(String code) {
        processCode = code;
    }

    public boolean equals(NonCfmProcess process) {
        return this == process;
    }
}
