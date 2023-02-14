package lims.api.common.domain.holiday;

import java.time.temporal.ChronoUnit;

public enum TimeUnit {
    DAY(ChronoUnit.DAYS),
    MONTH(ChronoUnit.MONTHS);

    private ChronoUnit chronoUnit;

    TimeUnit(ChronoUnit chronoUnit) {
        this.chronoUnit = chronoUnit;
    }

    public ChronoUnit chronoUnit() {
        return this.chronoUnit;
    }
}