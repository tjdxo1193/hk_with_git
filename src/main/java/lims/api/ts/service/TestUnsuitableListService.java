package lims.api.ts.service;

import lims.api.ts.vo.TestUnsuitableListVO;

import java.util.List;

public interface TestUnsuitableListService {
    List<TestUnsuitableListVO> findAll(TestUnsuitableListVO param);
    List<TestUnsuitableListVO> findResultItem(Integer ansIdx);
}
