package lims.api.ms.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lims.api.ms.dao.TestItemManageDao;
import lims.api.ms.service.TestItemManageService;
import lims.api.ms.vo.TestItemManageVO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TestItemManageServiceImpl implements TestItemManageService {

	private final TestItemManageDao testItemManageDao;
	
	@Override
	public List<TestItemManageVO> getList(TestItemManageVO vo) {
		return testItemManageDao.getList(vo);
	}
	
	@Override
	public int putTestItem(TestItemManageVO vo) {
		return testItemManageDao.putTestItem(vo);
	}
}
