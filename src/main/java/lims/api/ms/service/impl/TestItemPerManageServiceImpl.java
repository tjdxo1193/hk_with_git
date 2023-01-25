package lims.api.ms.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lims.api.ms.dao.TestItemPerManageDao;
import lims.api.ms.service.TestItemPerManageService;
import lims.api.ms.vo.TestItemPerManageVO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TestItemPerManageServiceImpl implements TestItemPerManageService {
	
	private final TestItemPerManageDao testItemPerManageDao;
	
	@Override
	public List<TestItemPerManageVO> getList(TestItemPerManageVO vo) {
		return testItemPerManageDao.getList(vo);
	}

	@Override
	public List<TestItemPerManageVO> getPerList(TestItemPerManageVO vo) {
		return testItemPerManageDao.getPerList(vo);
	}
	
	@Override
	public int putTestItemPer(TestItemPerManageVO vo) {
		return testItemPerManageDao.putTestItemPer(vo);
	}

}
