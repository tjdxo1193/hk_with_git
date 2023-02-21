package lims.api.ts.service;

import lims.api.ts.vo.TestInstructionVO;

import java.util.List;

public interface TestInstructionService {
    List<TestInstructionVO> getTestInstructList(TestInstructionVO request);
    List<TestInstructionVO> getTestAitm(TestInstructionVO request);

    void deleteRst(List<TestInstructionVO> request);
    void instruct(List<TestInstructionVO> request);

    int saveFile(TestInstructionVO request);
}
