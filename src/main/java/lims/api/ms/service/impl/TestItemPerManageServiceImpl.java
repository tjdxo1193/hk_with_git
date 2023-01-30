package lims.api.ms.service.impl;

import lims.api.integration.enums.ELNCmdType;
import lims.api.integration.enums.InterfaceResponseStatus;
import lims.api.integration.service.impl.IntegrationSender;
import lims.api.integration.vo.intergation.InterfaceSendVO;
import lims.api.ms.dao.TestItemPerManageDao;
import lims.api.ms.service.TestItemPerManageService;
import lims.api.ms.vo.TestItemPerManageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestItemPerManageServiceImpl implements TestItemPerManageService {
	
	private final TestItemPerManageDao testItemPerManageDao;

	private final IntegrationSender integrationSender;
	
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

	@Override
	public void elnSendTestItemMethod(TestItemPerManageVO param) {

		InterfaceSendVO.MethodByItem data = new InterfaceSendVO.MethodByItem(param.getAmitmCd());
		integrationSender.sendMethodByItem(param.getUseYn().equals("Y") ? ELNCmdType.C : ELNCmdType.D, data);

	}

	@Override
	public boolean alreadySentToELNOrNot(TestItemPerManageVO param) {
		String xStat = testItemPerManageDao.checkDuplicateAmitemCd(param);
		return InterfaceResponseStatus.S.equals(xStat);
	}

}
