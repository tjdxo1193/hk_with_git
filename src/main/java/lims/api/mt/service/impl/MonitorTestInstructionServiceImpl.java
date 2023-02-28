package lims.api.mt.service.impl;

import lims.api.common.exception.NoUpdatedDataException;
import lims.api.common.service.ApproveService;
import lims.api.common.service.UserService;
import lims.api.mt.dao.MonitorTestInstructionDao;
import lims.api.mt.enums.MonitorTestProcess;
import lims.api.mt.service.MonitorTestInstructionService;
import lims.api.mt.vo.MonitorTestInstructionVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MonitorTestInstructionServiceImpl implements MonitorTestInstructionService {

    private final MonitorTestInstructionDao dao;
    private final ApproveService approveService;
    private final UserService userService;

    @Override
    public List<MonitorTestInstructionVO> getMonitorTestInstructionList(MonitorTestInstructionVO request) {
        request.setAnsProcCd(MonitorTestProcess.MONITOR_TEST_INSTRUCTION.getProcessCode());
        request.setWithDelegateUserIds(userService.getDelegateAssignUserIdsWithMe(request.getUserId()));
        return dao.getMonitorTestInstructionList(request);
    }

    @Override
    public List<MonitorTestInstructionVO> getMonitorTestRst(MonitorTestInstructionVO request) {
        return dao.getMonitorTestRst(request);
    }

    @Override
    public void instruct(List<MonitorTestInstructionVO> request) {
        int result = 0;
        int assNo = 1;
        for (MonitorTestInstructionVO item : request) {
            item.setAnsProcCd(MonitorTestProcess.MONITOR_TEST_RESULT_INPUT.getProcessCode());
            approveService.approve(item.getAssAprReqIdx());
            item.setAssNo(assNo);
            result += dao.instruct(item);
            assNo++;
        }
        
        if (result != request.size()) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void deleteRst(List<MonitorTestInstructionVO> request) {
        int result = 0;
        for (MonitorTestInstructionVO item : request) {
            result += dao.deleteRst(item);
        }
        if (result != request.size()) {
            throw new NoUpdatedDataException();
        }

    }
}
