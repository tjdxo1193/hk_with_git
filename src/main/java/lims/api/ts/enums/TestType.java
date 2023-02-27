package lims.api.ts.enums;

import lims.api.integration.enums.TestRequestType;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum TestType {
    PURCHASE_ORDER("S0230001"),
    MANUFACTURE_ORDER("S0230002"),
    MANUAL("S0230003"),
    STABILITY("S0230004"),
    RETRY("S0230005"),
    VALIDATION("S0230006");

    private final String code;

    TestType(String code) {
        this.code = code;
    }

    public String getValue() {
        return code;
    }

    public boolean equals(String code) {
        return this.code.equals(code);
    }

    private static final Set<TestType> TEST_BY_SAP = Stream.of(
            PURCHASE_ORDER,
            MANUFACTURE_ORDER,
            MANUAL
    ).collect(Collectors.toSet());

    public static TestType of(String code) {
        return  Arrays.stream(values()).filter(type -> type.getValue().equals(code)).findAny().orElse(null);
    }

    /**
     * SAP 의뢰 건 접수 시 기존에 접수된 데이터가 없다면 최초 전송 시에는 TestType이 null로 들어옵니다.
     * 따라서 null일 때는 SAP으로부터 받은 의뢰건의 최초 접수건으로 판단합니다.
     */
    public static boolean bySAP(TestType testType) {
        return testType == null || TEST_BY_SAP.contains(testType);
    }

    public static boolean bySAP(String code) {
        return bySAP(of(code));
    }

    public static boolean byNotSAP(TestType testType) {
        return !bySAP(testType);
    }
}