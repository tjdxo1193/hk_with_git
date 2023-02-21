package lims.api.mt.service.impl;

import lims.api.common.exception.NoUpdatedDataException;
import lims.api.mt.dao.MonitorTestResultInputDao;
import lims.api.mt.enums.MonitorTestProcess;
import lims.api.mt.service.MonitorTestResultInputService;
import lims.api.mt.vo.MonitorTestResultInputVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MonitorTestResultInputServiceImpl implements MonitorTestResultInputService {

    private final MonitorTestResultInputDao dao;

    @Override
    public List<MonitorTestResultInputVO> getMonitorTestResultInputList(MonitorTestResultInputVO request) {
        List<String> processList = new ArrayList<>();
        processList.add(0, MonitorTestProcess.MONITOR_TEST_RESULT_INPUT.getProcessCode());
        processList.add(1, MonitorTestProcess.MONITOR_TEST_REVIEW_REJECT.getProcessCode());
        request.setTestProcessList(processList);
        return dao.getMonitorTestResultInputList(request);
    }

    @Override
    public List<MonitorTestResultInputVO> getMonitorTestRst(MonitorTestResultInputVO request) {
        return dao.getMonitorTestRst(request);
    }

    @Override
    public void updateRst(MonitorTestResultInputVO request) {
        int result = 0;
        int length = request.getEditedRowItems().size();
        if(length != 0) {
            for (MonitorTestResultInputVO item : request.getEditedRowItems()) {
                item.setRstProcCd(MonitorTestProcess.MONITOR_TEST_RESULT_INPUT_COMPLETED.getProcessCode());
                result += dao.updateRst(item);
            }
            if(length != result) {
                throw new NoUpdatedDataException();
            }
        }
        if(request.getSytJdg() == ""){
            int cnt = dao.getNullCount(request);
            if(cnt == 0){
                String judge = dao.getSytJdg(request);
                request.setSytJdg(judge);
            }
        }

        result = dao.updateSytJdg(request);
        if (result == 0) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void requestReview(MonitorTestResultInputVO request) {
        request.setAnsProcCd(MonitorTestProcess.MONITOR_TEST_REVIEW.getProcessCode());
        int result = dao.requestReview(request);
        if (result == 0) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void hold(MonitorTestResultInputVO request) {
        int result = dao.hold(request);
        if (result == 0) {
            throw new NoUpdatedDataException();
        }
    }
}
