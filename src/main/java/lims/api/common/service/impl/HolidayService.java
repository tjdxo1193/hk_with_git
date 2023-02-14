package lims.api.common.service.impl;

import lims.api.common.domain.holiday.HolidayCalculatorBySAPCalendar;
import lims.api.common.domain.holiday.HolidayCalculator;
import lims.api.schedule.dao.MonitorTestScheduleDao;
import lims.api.schedule.enums.WorkDay;
import lims.api.schedule.vo.HolidayConditionVO;
import lims.api.schedule.vo.HolidayVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class HolidayService {

    private final MonitorTestScheduleDao scheduleDao;

    public HolidayCalculator getCalculator() {
        return new HolidayCalculatorBySAPCalendar(getHolidays());
    }

    public HolidayCalculator getCalculator(String separator) {
        return new HolidayCalculatorBySAPCalendar(getHolidays(), separator);
    }

    private List<HolidayVO> getHolidays() {
        return scheduleDao.findHoliday(createHolidayCondition());
    }

    private HolidayConditionVO createHolidayCondition() {
        return HolidayConditionVO.builder()
                .workYn(WorkDay.HOLIDAY.getCode())
                .build();
    }

}