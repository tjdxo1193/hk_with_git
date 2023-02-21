package lims.api.ms.enums;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum SpecProgress {
    TEMPORARY_STORAGE("S0080100"),
    REVIEW_RETURN("S0080110"),
    REQUEST_REVIEW("S0080200"),
    APPROVAL_REJECTION("S0080210"),
    APPROVAL_REQUEST("S0080300"),
    APPROVED("S0080400"),

    SPEC_REMOVE("S0080900");

    private final String code;

    SpecProgress(String code){
        this.code = code;
    }

    public String getCode(){
        return code;
    }

    private static final Map<String, SpecProgress> BY_CODE =
            Stream.of(SpecProgress.values()).collect(Collectors.toMap(SpecProgress::getCode, Function.identity()));

    public static SpecProgress valueOfCode(String code) {
        return BY_CODE.get(code);
    }

    public static Set<String> getCodesRelatedToSpecProgress() {
        return Set.of(
                REQUEST_REVIEW.getCode(),
                APPROVAL_REJECTION.getCode()
        );
    }

    public boolean equals(String code) {
        return this.code.equals(code);
    }

}