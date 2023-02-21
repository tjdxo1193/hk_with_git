package lims.api.st.enums;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum AnsKndCode {
    LT("U0210001", "장기보존시험"),
    AC("U0210002", "가속시험"),
    OG("U0210003", "On-Going");

    private final String ansKnd;
    private final String ansKndNm;
    AnsKndCode(String code, String nm) {
        ansKnd = code;
        ansKndNm = nm;
    }

    private String getAnsKnd() {
        return ansKnd;
    }

    private String getAnsKndNm() {
        return ansKndNm;
    }

    private static final Map<String, AnsKndCode> BY_CODE = Stream.of(AnsKndCode.values()).collect(Collectors.toMap(AnsKndCode::getAnsKnd, Function.identity()));

    public static AnsKndCode valueOfCode(String code) {
        return BY_CODE.get(code);
    }
}
