package lims.api.ms.service;

import lims.api.ms.vo.TestMaterialVO;

import java.util.List;

public interface TestMaterialManageService {
    List<TestMaterialVO> getMasters(TestMaterialVO param);
    TestMaterialVO create(TestMaterialVO param);
    void update(TestMaterialVO param);
    void delete(TestMaterialVO param);
}