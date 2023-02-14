package lims.api.schedule.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
public class HolidayVO {
    private int year;
    private int month;
    private int day;

    public static HolidayVO of(LocalDate date) {
        HolidayVO vo = new HolidayVO();
        vo.setYear(date.getYear());
        vo.setMonth(date.getMonthValue());
        vo.setDay(date.getDayOfMonth());
        return vo;
    }
}