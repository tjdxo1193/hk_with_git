package lims.api.st.enums;

import lombok.Getter;

@Getter
public enum SbtAnsProcess {

    SAVE("S0290100"),
    APPROVE_REQUEST("S0290200"),
    APPROVE_REJECT("S0290210"),
    APPROVED("S0290300"),
    STOP_REQUEST("S0290400"),
    STOP_REJECT("S0290410"),
    STOP("S0290500"),
    STOP_CANCEL_REQUEST("S0290600"),
    STOP_CANCEL_REJECT("S0290610"),
    STOP_CANCEL("S0290700"),
    RESULT_APPROVED("S0290800");

    private final String processCode;
    SbtAnsProcess(String code) {
            processCode = code;
        }
    public boolean equals(SbtAnsProcess process) {
            return this == process;
        }
}
