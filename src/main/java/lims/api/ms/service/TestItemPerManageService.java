package lims.api.ms.service;

import java.util.List;

import lims.api.ms.vo.TestItemPerManageVO;

public interface TestItemPerManageService {
	List<TestItemPerManageVO> getList(TestItemPerManageVO vo);	
	List<TestItemPerManageVO> getPerList(TestItemPerManageVO vo);
	int putTestItemPer(TestItemPerManageVO vo);
}
