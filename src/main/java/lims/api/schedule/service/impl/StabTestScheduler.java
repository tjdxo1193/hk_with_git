package lims.api.schedule.service.impl;

import lims.api.common.domain.holiday.HolidayCalculator;
import lims.api.common.service.impl.HolidayService;
import lims.api.schedule.dao.StabTestScheduleDao;
import lims.api.schedule.service.Scheduler;
import lims.api.schedule.vo.StabTestConditionVO;
import lims.api.schedule.vo.StabTestSearchVO;
import lims.api.schedule.vo.StabTestVO;
import lims.api.st.enums.SbtAnsProcess;
import lims.api.st.enums.SbtAnsSttProcess;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class StabTestScheduler implements Scheduler {

    private final StabTestScheduleDao scheduleDao;
    private final HolidayService holidayService;

    @Scheduled(cron = "0 0 2 * * *")
    @Override
    public void run() {
        LocalDate standardDay = LocalDate.now().plusDays(5);
        HolidayCalculator calculator = holidayService.getCalculator();

        List<StabTestVO> stabs = getStabTestToSave(standardDay, calculator);

        if (CollectionUtils.isEmpty(stabs)) {
            log.info("[StabTestScheduler] No exists data to save.");
            return;
        }
        save(stabs);
    }

    private List<StabTestVO> getStabTestToSave(LocalDate standardDay, HolidayCalculator calculator) {
        List<StabTestSearchVO> stabs = scheduleDao.findStabs(createFindStabCondition());
        List<StabTestVO> willSaveData = new ArrayList<>();

        LocalDate targetDate;
        for (StabTestSearchVO stab : stabs) {
            targetDate = toDate(stab.getAnsDt());

            if (targetDate.isBefore(standardDay) || targetDate.isEqual(standardDay)) {
                // 시험일자가 기준일보다 이전이거나 동일하면 시험 생성함.
                if (calculator.isHoliday(targetDate)) {
                    targetDate = calculator.nextWorkday(targetDate);
                }

                willSaveData.add(toSaveVO(targetDate, stab));
            }
        }
        return willSaveData;
    }

    private void save(List<StabTestVO> monitors) {
    }

    private StabTestVO toSaveVO(LocalDate targetDate, StabTestSearchVO stab) {
        return StabTestVO.builder()
                .build();
    }

    private StabTestConditionVO createFindStabCondition() {
        return StabTestConditionVO.builder()
                .sbtAnsStt(SbtAnsSttProcess.TEST_BEFORE.getProcessCode())
                .sbtAnsProc(SbtAnsProcess.APPROVED.getSbtAnsProc())
                .build();
    }

    private LocalDate toDate(String dateStringWithHypen) {
        String[] arr = dateStringWithHypen.split("-");
        int year = Integer.parseInt(arr[0]);
        int month = Integer.parseInt(arr[1]);
        int day = Integer.parseInt(arr[2]);
        return LocalDate.of(year, month, day);
    }
}