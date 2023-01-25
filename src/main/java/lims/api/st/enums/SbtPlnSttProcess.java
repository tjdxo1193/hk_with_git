package lims.api.st.enums;

import lombok.Getter;

@Getter
public enum SbtPlnSttProcess {
    SAVE("S0300100"),
    PLN_APPROVE_REQUEST("S0300200"),
    PLN_BEFORE("S0300300"),
    PLN_TEST("S0300400"),
    PLN_STOP("S0300500"),
    PLN_END("S0300600");

    private final String processCode;
    SbtPlnSttProcess(String code) {
            processCode = code;
        }
    public boolean equals(SbtPlnSttProcess process) {
            return this == process;
        }
}
