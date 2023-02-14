package lims.api.common.domain.holiday;

import java.time.LocalDate;

public interface HolidayCalculator {

    /**
     * date의 다음 일자 A를 반환합니다.
     * 일자 A가 휴일이라면 가장 가까운 근무 일자를 찾아 반환합니다.
     */
    LocalDate nextWorkday(LocalDate date);

    LocalDate nextWorkday(String dateStr);

    /**
     * date에 plus를 더한 일자 A를 반환합니다.
     * date와 일자 A 사이에 휴일이 존재한다면 해당 휴일은 카운트하지 않습니다.
     * 즉, 휴일을 제외한 근무 일자만 plus한 일자를 반환합니다.
     */
    LocalDate plusWorkdays(LocalDate date, int plus, TimeUnit unit);

    LocalDate plusWorkdays(String dateStr, int plus, TimeUnit unit);

    /**
     * date에 plus를 더한 일자 A를 반환합니다.
     * 일자 A가 휴일이라면 가장 가까운 다음 근무일을 반환합니다.
     *
     * plusWorkdays는 일자 사이의 휴일이 있다면 제외하고 더하지만,
     * plusDays는 사이의 일자는 고려하지 않고 그대로 더하는 것이 차이입니다.
     */
    LocalDate plusDays(LocalDate date, int plus, TimeUnit unit);

    LocalDate plusDays(String dateStr, int plus, TimeUnit unit);

    /**
     * LocalDate는 toString()으로 문자열 변환 시 ISO 포맷인 uuuu-MM-dd 형태로 변환됩니다.
     * 위 포맷 이외에 다른 포맷팅이 필요할 때 사용하며 ISO 표준 포맷 문자열을 이용해 pattern을 작성합니다.
     *
     * @see https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
     */
    String format(LocalDate date, String pattern);


    boolean isHoliday(LocalDate date);

    boolean isHoliday(String dateStr);


    boolean isWorkday(LocalDate date);

    boolean isWorkday(String dateStr);

}