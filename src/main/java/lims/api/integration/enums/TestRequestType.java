package lims.api.integration.enums;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum TestRequestType {
    A, // 구매
    B, // 생산
    C; // 수동(= SAP에서 수동으로 보낸다는 의미. 즉 LIMS에서는 재시험)

    private static final Map<String, TestRequestType> SHIPT_TARGET_TYPES = Stream.of(A, B, C).collect(Collectors.toMap(Enum::name, o -> o));

    public static boolean isShiptTarget(String v) {
        return SHIPT_TARGET_TYPES.containsKey(v);
    }
}