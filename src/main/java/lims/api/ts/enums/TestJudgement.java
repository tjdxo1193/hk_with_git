package lims.api.ts.enums;

import lombok.Getter;

@Getter
public enum TestJudgement {
    SUITABLE("S0110001"),
    UNSUITABLE("S0110002"),
    CONDITIONAL_SUITABLE("S0110003");

    private final String judgementCode;

    TestJudgement(String code) {
        judgementCode = code;
    }

    public boolean equals(TestJudgement process) {
        return this == process;
    }

}
