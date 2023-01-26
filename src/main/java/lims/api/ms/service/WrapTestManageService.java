package lims.api.ms.service;

import lims.api.ms.vo.WrapTestManageVO;

import java.util.List;

public interface WrapTestManageService {

	List<WrapTestManageVO> getList(WrapTestManageVO param);

	WrapTestManageVO getOne(WrapTestManageVO param);
	List<WrapTestManageVO> getSapList(WrapTestManageVO param);
	List<WrapTestManageVO> getVersion(WrapTestManageVO param);
	// tab 1
	List<WrapTestManageVO> getTestItem(WrapTestManageVO param);
	// tab 2
	List<WrapTestManageVO> getSpec(WrapTestManageVO param);
	
	Integer putQmPkGa(WrapTestManageVO baseData, List<WrapTestManageVO> testItemList, List<WrapTestManageVO> deleteTestItemList);
	Integer insertVersion(WrapTestManageVO param);
	Integer putTestItem(WrapTestManageVO param);
	Integer deleteTestItem(WrapTestManageVO param);
	Integer putWrapTest(WrapTestManageVO param);
	Integer updateWrapTest(WrapTestManageVO param);
	Integer updateWrapTestUseYn(WrapTestManageVO param);
	void approval(WrapTestManageVO param);
	Integer getSapPrdhaDuplicateCheck(WrapTestManageVO param);
}
