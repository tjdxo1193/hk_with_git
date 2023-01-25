package lims.api.in.service;

import java.util.List;

import lims.api.in.vo.InstHisManageVO;

public interface InstHisManageService {
	List<InstHisManageVO> findAll(InstHisManageVO param);

	int save(InstHisManageVO param);

	void saveAsList(List<InstHisManageVO> list);

	int update(InstHisManageVO param);

	void delete(InstHisManageVO param);

	void requestApprove(InstHisManageVO param);
}
