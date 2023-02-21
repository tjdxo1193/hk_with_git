package lims.api.ms.service;

import lims.api.ms.vo.TestCycleVO;
import lims.api.ms.vo.TestMaterialVO;

import java.util.List;

public interface TestCycleService {
    List<TestCycleVO> getList(TestCycleVO param);
    void create(TestCycleVO param);
    void update(TestCycleVO param);
    void delete(TestCycleVO param);
}