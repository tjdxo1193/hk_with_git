package lims.api.schedule.service.impl;

import lims.api.common.domain.holiday.HolidayCalculator;
import lims.api.common.domain.holiday.TimeUnit;
import lims.api.common.enums.PlantType;
import lims.api.common.service.impl.HolidayService;
import lims.api.schedule.dao.StabTestScheduleDao;
import lims.api.schedule.service.Scheduler;
import lims.api.schedule.vo.StabTestConditionVO;
import lims.api.schedule.vo.StabTestSearchVO;
import lims.api.schedule.vo.StabTestSpecAitmVO;
import lims.api.schedule.vo.StabTestVO;
import lims.api.st.enums.SbtAnsProcess;
import lims.api.st.enums.SbtAnsSttProcess;
import lims.api.st.enums.SbtPlnSttProcess;
import lims.api.ts.enums.TestProcess;
import lims.api.ts.enums.TestType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        updatePlanStatus(stabs);
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

                willSaveData.add(toSaveVO(calculator, targetDate, stab));
            }
        }
        return willSaveData;
    }

    private void save(List<StabTestVO> monitors) {
        for (StabTestVO monitor : monitors) {
            scheduleDao.createTestRequestProcess(monitor);

            Integer newAnsIdx = monitor.getNewAnsIdx();
            Integer aitmSpecIdx = scheduleDao.findAitmSpecIdx(monitor);
            savePitmResult(newAnsIdx, aitmSpecIdx, monitor);

            StabTestConditionVO param = StabTestConditionVO.builder()
                    .plntCd(monitor.getPlntCd())
                    .sbtAnsIdx(monitor.getSbtAnsIdx())
                    .sbtAnsStt(SbtAnsSttProcess.TEST_ON_PROCESS.getProcessCode())
                    .udtUid(PlantType.SYSTEM.getUid())
                    .udtIp(PlantType.SYSTEM.getIp())
                    .build();
            scheduleDao.beginStabTest(param);
        }
    }

    private void updatePlanStatus(List<StabTestVO> monitors) {
        Set<StabTestVO.StabPlanStatus> planStatuses = new HashSet<>();
        for (StabTestVO monitor : monitors) {
            String statusCode = scheduleDao.findPlanStatus(monitor);

            StabTestVO.StabPlanStatus status = StabTestVO.StabPlanStatus.builder()
                    .plntCd(monitor.getPlntCd())
                    .sbtPlnIdx(monitor.getSbtPlnIdx())
                    .sbtPlnStt(statusCode)
                    .build();
            planStatuses.add(status);
        }

        for (StabTestVO.StabPlanStatus status : planStatuses) {
            if (SbtPlnSttProcess.PLN_BEFORE.getProcessCode().equals(status.getSbtPlnStt())) {
                status.setUpdateSbtPlnStt(SbtPlnSttProcess.PLN_TEST.getProcessCode());
                status.setUdtIp(PlantType.SYSTEM.getIp());
                status.setUdtUid(PlantType.SYSTEM.getUid());
                scheduleDao.beginPlanStatus(status);
            }
        }
    }

    private void savePitmResult(Integer newAnsIdx, Integer aitmSpecIdx, StabTestVO monitor) {
        StabTestVO condition = StabTestVO.builder()
                .plntCd(monitor.getPlntCd())
                .sbtPlnIdx(monitor.getSbtPlnIdx())
                .ansIdx(monitor.getAnsIdx())
                .aitmSpecIdx(aitmSpecIdx)
                .build();
        List<StabTestSpecAitmVO> specAitms = scheduleDao.findSpecAitmKeyByAmitmCode(condition);

        if (CollectionUtils.isEmpty(specAitms)) {
            log.error(
                    "[StabTestScheduler.save()] Not found spec aitm. plantCode: {}, ansIdx: {}, aitmSpecIdx: {}",
                    monitor.getPlntCd(),
                    monitor.getAnsIdx(),
                    monitor.getAitmSpecIdx());
//            throw new RuntimeException("Not found spec aitm. Check the above error log.");
        }

        for (StabTestSpecAitmVO aitm : specAitms) {
            aitm.setAnsIdx(newAnsIdx);
            aitm.setAitmSpecIdx(aitmSpecIdx);
            aitm.setRstProcCd(TestProcess.TEST_RESULT_INPUT_WAITING.getProcessCode());
            scheduleDao.createTestRequestPitmResult(aitm);
        }
    }

    private StabTestVO toSaveVO(HolidayCalculator calculator, LocalDate targetDate, StabTestSearchVO stab) {
        return StabTestVO.builder()
                .plntCd(stab.getPlntCd())
                .ansIdx(stab.getAnsIdx())
                .sbtAnsIdx(stab.getSbtAnsIdx())
                .sbtPlnIdx(stab.getSbtPlnIdx())
                .ansProcCd(TestProcess.TEST_RECEIPT.getProcessCode())
                .ansTyp(TestType.STABILITY.getValue())
                .ansEdt(targetDate.toString())
                .cplRqmDt(calculator.plusDays(targetDate, 5, TimeUnit.DAY).toString())
                .reqDt(LocalDate.now().toString())
                .reqDs(LocalDateTime.now())
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