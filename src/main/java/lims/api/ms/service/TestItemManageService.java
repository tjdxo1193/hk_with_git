package lims.api.ms.service;

import java.util.List;

import lims.api.ms.vo.TestItemManageVO;

public interface TestItemManageService {
	List<TestItemManageVO> getList(TestItemManageVO vo);
	int putTestItem(TestItemManageVO vo);
}
