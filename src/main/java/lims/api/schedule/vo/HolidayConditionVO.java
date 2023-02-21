package lims.api.schedule.vo;

import lims.api.integration.annotation.EnumType;
import lims.api.schedule.enums.WorkDay;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HolidayConditionVO {
    @EnumType(WorkDay.class)
    private String workYn;
}