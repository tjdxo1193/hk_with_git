package lims.api.schedule.enums;

import java.time.temporal.ChronoUnit;
import java.util.Arrays;

public enum MonitorCycleUnit {
    DAY("S0090001", ChronoUnit.DAYS),
    MONTH("S0090002", ChronoUnit.MONTHS);

    private final String code;
    private final ChronoUnit unit;

    MonitorCycleUnit(String code, ChronoUnit unit) {
        this.code = code;
        this.unit = unit;
    }

    public String getCode() {
        return this.code;
    }

    public ChronoUnit getUnit() {
        return this.unit;
    }

    public static MonitorCycleUnit of(String code) {
        return Arrays.stream(MonitorCycleUnit.values())
                .filter(unit -> unit.getCode().equals(code))
                .findAny()
                .orElse(null);
    }
}