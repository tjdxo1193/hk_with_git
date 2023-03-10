package lims.api.schedule.service.impl;

import lims.api.common.domain.holiday.HolidayCalculator;
import lims.api.common.enums.UseType;
import lims.api.common.service.impl.HolidayService;
import lims.api.ms.enums.SpecProgress;
import lims.api.mt.enums.MonitorTestProcess;
import lims.api.schedule.dao.MonitorTestScheduleDao;
import lims.api.schedule.enums.MonitorCycleUnit;
import lims.api.schedule.service.Scheduler;
import lims.api.schedule.vo.MonitorTestConditionVO;
import lims.api.schedule.vo.MonitorTestSearchVO;
import lims.api.schedule.vo.MonitorTestVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MonitorTestScheduler implements Scheduler {

    private final MonitorTestScheduleDao scheduleDao;
    private final HolidayService holidayService;

    @Scheduled(cron = "0 0 2 * * *")
    @Override
    public void run() {
        LocalDate standardDay = LocalDate.now();
        HolidayCalculator calculator = holidayService.getCalculator();

        List<MonitorTestVO> monitors = getMonitorTestToSave(standardDay, calculator);

        if (CollectionUtils.isEmpty(monitors)) {
            log.info("[MonitorTestScheduler] No exists data to save.");
            return;
        }
        save(monitors);
    }

    private List<MonitorTestVO> getMonitorTestToSave(LocalDate standardDay, HolidayCalculator calculator) {
        List<MonitorTestSearchVO> monitors = scheduleDao.findMonitors(createFindMonitorCondition());
        List<MonitorTestVO> willSaveData = new ArrayList<>();

        int interval;
        LocalDate targetDate, lastCreatedDate;
        MonitorCycleUnit cycleUnit;
        for (MonitorTestSearchVO monitor : monitors) {
            interval = monitor.getAnsItv();
            cycleUnit = MonitorCycleUnit.of(monitor.getAnsCylDiv());
            targetDate = getUpdateDate(monitor);
            lastCreatedDate = targetDate;

            while(lastCreatedDate.isBefore(standardDay) || lastCreatedDate.isEqual(standardDay)) {
                if (calculator.isHoliday(lastCreatedDate)) {
                    lastCreatedDate = calculator.nextWorkday(lastCreatedDate);
                }
                /**
                 * ?????? ?????????????????? ???????????? ????????? ?????????
                 * ?????? ?????????????????? ????????? ????????? ???????????? ????????? ?????? ?????? ???????????? ???????????? ??? ???????????????.
                 */
                targetDate = targetDate.plus(interval, cycleUnit.getUnit());
                willSaveData.add(toSaveVO(calculator, targetDate, lastCreatedDate, monitor));
                lastCreatedDate = targetDate;
            }
        }
        return willSaveData;
    }

    private void save(List<MonitorTestVO> monitors) {
        MonitorTestConditionVO mitm;
        for (MonitorTestVO param : monitors) {
            scheduleDao.createMonitorTestRequest(param);

            mitm = MonitorTestConditionVO.builder()
                    .plntCd(param.getPlntCd())
                    .mitmCd(param.getMitmCd())
                    .finsReqDt(param.getFinsReqDt())
                    .build();
            scheduleDao.updateLastCreatedDateInMonitorItem(mitm);
        }
    }

    private MonitorTestVO toSaveVO(HolidayCalculator calculator, LocalDate targetDate, LocalDate updatedDate, MonitorTestSearchVO monitor) {
        LocalDate completeRequestDate = updatedDate.plusDays(7);
        if (calculator.isHoliday(completeRequestDate)) {
            completeRequestDate = calculator.nextWorkday(completeRequestDate);
        }

        return MonitorTestVO.builder()
                .plntCd(monitor.getPlntCd())
                .mitmCd(monitor.getMitmCd())
                .mitmSpecIdx(monitor.getMitmSpecIdx())
                .ansProcCd(MonitorTestProcess.MONITOR_TEST_REQUEST.getProcessCode())
                .reqDt(updatedDate.toString())
                .cplRqmDt(completeRequestDate.toString())
                .finsReqDt(targetDate.toString())
                .reqCanlYn(UseType.N)
                .build();
    }

    private MonitorTestConditionVO createFindMonitorCondition() {
        return MonitorTestConditionVO.builder()
                .specProcCd(SpecProgress.APPROVED.getCode())
                .build();
    }

    private LocalDate getUpdateDate(MonitorTestSearchVO vo) {
        String updateDate = Optional.ofNullable(vo.getFinsReqDt()).orElse(vo.getAnsStrDt());
        return toDate(updateDate);
    }

    private LocalDate toDate(String dateStringWithHypen) {
        String[] arr = dateStringWithHypen.split("-");
        int year = Integer.parseInt(arr[0]);
        int month = Integer.parseInt(arr[1]);
        int day = Integer.parseInt(arr[2]);
        return LocalDate.of(year, month, day);
    }

}