package lims.api.schedule.service.impl;

import lims.api.sc.enums.InterfaceSucceedDiv;
import lims.api.sc.service.InspectProductionPerformanceService;
import lims.api.sc.vo.InspectProductionPerformanceVO;
import lims.api.schedule.dao.InputPerformanceScheduleDao;
import lims.api.schedule.service.Scheduler;
import lims.api.schedule.vo.InputPerformVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManufactureInputPerformanceScheduler implements Scheduler {

    private final InputPerformanceScheduleDao scheduleDao;

    private final InspectProductionPerformanceService service;

//    @Scheduled(cron = "0 5 1 * * *")
    @Override
    public void run() {
        List<InspectProductionPerformanceVO> performs = service.find(createCondition());
        List<InspectProductionPerformanceVO> noYetSentPerforms = removeAlreadySentSuccessData(performs);

        service.send(noYetSentPerforms);
    }

    private List<InspectProductionPerformanceVO> removeAlreadySentSuccessData(List<InspectProductionPerformanceVO> performs) {
        List<InputPerformVO.Manufacture> failedPerforms = scheduleDao.findManufactureFailedRecord(InterfaceSucceedDiv.FAIL.getType());
        Set<Integer> failedKeys = failedPerforms.stream().map(InputPerformVO.Manufacture::getIspPdtPfaIdx).collect(Collectors.toSet());
        return performs.stream()
                .filter(vo -> vo.getIspPdtPfaIdx() == null || failedKeys.contains(vo.getIspPdtPfaIdx()))
                .collect(Collectors.toList());
    }

    private InspectProductionPerformanceVO createCondition() {
        LocalDate yesterday = LocalDate.now().minusDays(1);

        List<String> conditionDateList = new ArrayList<>();
        conditionDateList.add(yesterday.toString());
        conditionDateList.add(yesterday.toString());

        InspectProductionPerformanceVO condition = new InspectProductionPerformanceVO();
        condition.setIspReqDtList(conditionDateList);
        return condition;
    }
}