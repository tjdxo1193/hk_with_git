package lims.api.pr.enums;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum LabelPrtProcess {
    LABEL_PRINT_WAIT("S0350100"),         // 사용 안함
    RE_LABEL_PRINT_REQUEST("S0350200"),
    RE_LABEL_PRINT_REJECT("S0350210"),
    RE_LABEL_PRINT_APPROVED("S0350300"),
    LABEL_PRINT_PROCESSED("S0350400"),
    LABEL_PRINT_CANCEL("S0350500");

    private final String code;

    LabelPrtProcess(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static final Map<String, LabelPrtProcess> BY_CODE() {
        return Stream.of(LabelPrtProcess.values()).collect(Collectors.toMap(LabelPrtProcess::getCode, Function.identity()));
    }

    public static LabelPrtProcess valueOfCode(String code) {
        return BY_CODE().get(code);
    }
}
