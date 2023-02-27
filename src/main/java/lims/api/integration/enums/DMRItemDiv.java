package lims.api.integration.enums;

import java.util.Arrays;
import java.util.stream.Stream;

public enum DMRItemDiv {
    E,  // 의료기기
    I,  // 공산품
    Q,  // 의약외품
    C;  // 화장품

    public static DMRItemDiv of(String code) {
        return Arrays.stream(values())
                .filter(div -> div.name().equals(code))
                .findAny()
                .orElse(null);
    }

    public static boolean isDMRTarget(String code) {
        return Stream.of(Q, C)
                .anyMatch(div -> div.name().equals(code));
    }

    public static boolean isCosmetic(String code) {
        return C.name().equals(code);
    }

    public static boolean isQuasiDrug(String code) {
        return Q.name().equals(code);
    }
}