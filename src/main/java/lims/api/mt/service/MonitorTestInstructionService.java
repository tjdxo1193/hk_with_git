package lims.api.mt.service;

import lims.api.mt.vo.MonitorTestInstructionVO;

import java.util.List;

public interface MonitorTestInstructionService {
    List<MonitorTestInstructionVO> getMonitorTestInstructionList(MonitorTestInstructionVO request);

    List<MonitorTestInstructionVO> getMonitorTestRst(MonitorTestInstructionVO request);

    void instruct(List<MonitorTestInstructionVO> request);

    void deleteRst(List<MonitorTestInstructionVO> request);
}
