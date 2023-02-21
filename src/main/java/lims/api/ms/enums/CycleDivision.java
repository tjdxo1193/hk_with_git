package lims.api.ms.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum CycleDivision {
    Day ("Day","S0090001", "D"),
    Month ("Month", "S0090002" , "M")
    ;

    private final String engName;
    private final String code;
    private final String ruleName;

    CycleDivision(String engName, String code, String ruleName){
        this.engName = engName;
        this.code = code;
        this.ruleName = ruleName;
    };

    public String getEngName(){
        return engName;
    }

    public String getCode(){
        return code;
    }
    public String getRuleName(){
        return ruleName;
    }


    private static final Map<String, CycleDivision> BY_CODE =
            Stream.of(CycleDivision.values()).collect(Collectors.toMap(CycleDivision::getCode, Function.identity()));

    public static CycleDivision valueOfCode(String code) {
        return BY_CODE.get(code);
    }
}