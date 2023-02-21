package lims.api.common.domain.holiday;

import lims.api.schedule.vo.HolidayVO;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HolidayCalculatorBySAPCalendar implements HolidayCalculator {

    private final String separator;
    private final Set<HolidayVO> holidays;

    public HolidayCalculatorBySAPCalendar(List<HolidayVO> holidayList) {
        holidays = new HashSet<>(holidayList);
        this.separator = "-";
    }

    public HolidayCalculatorBySAPCalendar(List<HolidayVO> holidayList, String separator) {
        holidays = new HashSet<>(holidayList);
        this.separator = separator;
    }

    private LocalDate getPlusDaysAndNextWorkDay(LocalDate date, int plus, TimeUnit unit) {
        LocalDate nextDate = date.plus(plus, unit.chronoUnit());
        return isHoliday(nextDate) ? getNextWorkday(nextDate) : nextDate;
    }

    private LocalDate getPlusDaysExcludingHoliday(LocalDate date, int plus, TimeUnit unit) {
        LocalDate nextDate = date;
        LocalDate targetDate = date.plus(plus, unit.chronoUnit());

        long diffDays = ChronoUnit.DAYS.between(nextDate, targetDate);
        for (int i=0; i < diffDays; i++) {
            nextDate = getNextWorkday(nextDate);
        }
        return nextDate;
    }

    private LocalDate getNextWorkday(LocalDate date) {
        LocalDate nextDate = date.plusDays(1);
        int i = 0;
        int limit = holidays.size();
        while(isHoliday(nextDate)) {
            if (limit <= i) {
                return nextDate;
            }
            nextDate = nextDate.plusDays(1);
            i++;
        }
        return nextDate;
    }

    private boolean isSAPHoliday(LocalDate date) {
        return holidays.contains(HolidayVO.of(date));
    }

    private boolean isWeekend(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return DayOfWeek.SATURDAY == dayOfWeek || DayOfWeek.SUNDAY == dayOfWeek;
    }

    private LocalDate toDate(String s, String separator) {
        String[] arr = s.split(separator);
        int year = Integer.parseInt(arr[0]);
        int month = Integer.parseInt(arr[1]);
        int day = Integer.parseInt(arr[2]);
        return LocalDate.of(year, month, day);
    }

    @Override
    public LocalDate nextWorkday(String dateStr) {
        LocalDate date = toDate(dateStr, separator);
        return nextWorkday(date);
    }

    @Override
    public LocalDate nextWorkday(LocalDate date) {
        return getNextWorkday(date);
    }

    @Override
    public LocalDate plusWorkdays(LocalDate date, int plus, TimeUnit unit) {
        return getPlusDaysExcludingHoliday(date, plus, unit);
    }

    @Override
    public LocalDate plusWorkdays(String dateStr, int plus, TimeUnit unit) {
        LocalDate date = toDate(dateStr, separator);
        return plusWorkdays(date, plus, unit);
    }

    @Override
    public LocalDate plusDays(LocalDate date, int plus, TimeUnit unit) {
        return getPlusDaysAndNextWorkDay(date, plus, unit);
    }

    @Override
    public LocalDate plusDays(String dateStr, int plus, TimeUnit unit) {
        LocalDate date = toDate(dateStr, separator);
        return getPlusDaysAndNextWorkDay(date, plus, unit);
    }

    @Override
    public String format(LocalDate date, String pattern) {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    @Override
    public boolean isHoliday(LocalDate date) {
        return isWeekend(date) || isSAPHoliday(date);
    }

    @Override
    public boolean isHoliday(String dateStr) {
        LocalDate date = toDate(dateStr, separator);
        return isHoliday(date);
    }

    @Override
    public boolean isWorkday(LocalDate date) {
        return !isHoliday(date);
    }

    @Override
    public boolean isWorkday(String dateStr) {
        return !isHoliday(dateStr);
    }

}