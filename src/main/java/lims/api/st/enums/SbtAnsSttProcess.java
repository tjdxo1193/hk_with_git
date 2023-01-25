package lims.api.st.enums;

import lombok.Getter;

@Getter
public enum SbtAnsSttProcess {

    TEST_BEFORE("S0310100"),
    TEST_ON_PROCESS("S0310200"),
    TEST_STOP("S0310300"),
    TEST_END("S0310400");


    private final String processCode;
    SbtAnsSttProcess(String code) {
            processCode = code;
        }
    public boolean equals(SbtAnsSttProcess process) {
            return this == process;
        }
}
