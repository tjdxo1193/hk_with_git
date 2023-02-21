package lims.api.schedule.service.impl;

import lims.api.common.enums.PlantType;
import lims.api.sc.enums.InterfaceSucceedDiv;
import lims.api.sc.service.InspectPurchasingPerformanceService;
import lims.api.sc.vo.InspectPurchasingPerformanceVO;
import lims.api.schedule.dao.InputPerformanceScheduleDao;
import lims.api.schedule.service.Scheduler;
import lims.api.schedule.vo.InputPerformVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PurchaseInputPerformanceScheduler implements Scheduler {

    private final InputPerformanceScheduleDao scheduleDao;

    private final InspectPurchasingPerformanceService service;

//    @Scheduled(cron = "0 0 1 1 * *")
    @Override
    public void run() {
        List<InspectPurchasingPerformanceVO> sejongPerforms = service.find(createCondition(PlantType.SEJONG));
        List<InspectPurchasingPerformanceVO> bucheonPerforms = service.find(createCondition(PlantType.BUCHEON));

        List<InspectPurchasingPerformanceVO> performs = new ArrayList<>();
        performs.addAll(sejongPerforms);
        performs.addAll(bucheonPerforms);

        List<InspectPurchasingPerformanceVO> noYetSentPerforms = removeAlreadySentSuccessData(performs);

        service.send(noYetSentPerforms);
    }

    private List<InspectPurchasingPerformanceVO> removeAlreadySentSuccessData(List<InspectPurchasingPerformanceVO> performs) {
        List<InputPerformVO.Purchase> failedPerforms = scheduleDao.findPurchaseFailedRecord(InterfaceSucceedDiv.FAIL.getType());
        Set<Integer> failedKeys = failedPerforms.stream().map(InputPerformVO.Purchase::getIspPhsPfaIdx).collect(Collectors.toSet());
        return performs.stream()
                .filter(vo -> vo.getIspPhsPfaIdx() == null || failedKeys.contains(vo.getIspPhsPfaIdx()))
                .collect(Collectors.toList());
    }

    private InspectPurchasingPerformanceVO createCondition(PlantType plantType) {
        LocalDate today = LocalDate.now().minusMonths(1);

        InspectPurchasingPerformanceVO condition = new InspectPurchasingPerformanceVO();
        condition.setPlntCd(plantType.getCode());
        condition.setFiscalYr(today.format(DateTimeFormatter.ofPattern("yyyy")));
        condition.setMonth(today.format(DateTimeFormatter.ofPattern("MM")));
        return condition;
    }
}