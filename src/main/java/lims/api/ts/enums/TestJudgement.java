package lims.api.ts.enums;

import lims.api.integration.enums.TestResultType;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum TestJudgement {
    SUITABLE("S0110001", TestResultType.C),
    UNSUITABLE("S0110002", TestResultType.N),
    CONDITIONAL_SUITABLE("S0110003", TestResultType.P);

    private final String judgementCode;
    private final TestResultType resultType;

    TestJudgement(String code, TestResultType resultType) {
        this.judgementCode = code;
        this.resultType = resultType;
    }

    public boolean equals(TestJudgement process) {
        return this == process;
    }

    public TestResultType resultType() {
        return resultType;
    }

    public static TestJudgement of(String code) {
        return Arrays.stream(values()).filter(e -> e.judgementCode.equals(code)).findAny().orElse(null);
    }

    public static TestJudgement min(TestJudgement j1, TestJudgement j2) {
        if (j1 == j2) {
            return j1;
        }
        if (j1 == UNSUITABLE) {
            return j1;
        }
        if (j2 == UNSUITABLE) {
            return j2;
        }
        if (j1 == CONDITIONAL_SUITABLE) {
            return j1;
        }
        if (j2 == CONDITIONAL_SUITABLE) {
            return j2;
        }
        return j1;
    }

}