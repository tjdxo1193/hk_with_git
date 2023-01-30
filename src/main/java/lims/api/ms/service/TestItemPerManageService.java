package lims.api.ms.service;

import lims.api.ms.vo.TestItemPerManageVO;

import java.util.List;

public interface TestItemPerManageService {
	List<TestItemPerManageVO> getList(TestItemPerManageVO vo);	
	List<TestItemPerManageVO> getPerList(TestItemPerManageVO vo);
	int putTestItemPer(TestItemPerManageVO vo);

	void elnSendTestItemMethod(TestItemPerManageVO param);
	boolean alreadySentToELNOrNot(TestItemPerManageVO param);
}
