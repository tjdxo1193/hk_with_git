package lims.api.st.enums;

import lombok.Getter;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    private final String sbtAnsProc;
    SbtAnsProcess(String code) { sbtAnsProc = code; }
    public String getSbtAnsProc() {
        return sbtAnsProc;
    }
    public boolean equals(SbtAnsProcess sbtAnsProc) {
            return this == sbtAnsProc;
        }
    private static final Map<String, SbtAnsProcess> BY_CODE = Stream.of(SbtAnsProcess.values()).collect(Collectors.toMap(SbtAnsProcess::getSbtAnsProc, Function.identity()));

    public static SbtAnsProcess valueOfCode(String code) {
        return BY_CODE.get(code);
    }

    public static SbtAnsProcess getApproveCode(String code) {
        SbtAnsProcess current = valueOfCode(code);
        SbtAnsProcess next = null;

        if(SbtAnsProcess.APPROVE_REQUEST.equals(current)) {
            next = SbtAnsProcess.APPROVED;
        }
        if(SbtAnsProcess.STOP_REQUEST.equals(current)) {
            next = SbtAnsProcess.STOP;
        }
        if(SbtAnsProcess.STOP_CANCEL_REQUEST.equals(current)) {
            next = SbtAnsProcess.STOP_CANCEL;
        }

        return next;
    }

    public static SbtAnsProcess getRejectCode(String code) {
        SbtAnsProcess current = valueOfCode(code);
        SbtAnsProcess next = null;

        if(SbtAnsProcess.APPROVE_REQUEST.equals(current)) {
            next = SbtAnsProcess.APPROVE_REJECT;
        }
        if(SbtAnsProcess.STOP_REQUEST.equals(current)) {
            next = SbtAnsProcess.STOP_REJECT;
        }
        if(SbtAnsProcess.STOP_CANCEL_REQUEST.equals(current)) {
            next = SbtAnsProcess.STOP_CANCEL_REJECT;
        }

        return next;
    }

}
