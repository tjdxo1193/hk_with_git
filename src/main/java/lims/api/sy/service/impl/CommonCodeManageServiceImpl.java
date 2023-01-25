package lims.api.sy.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lims.api.common.exception.NoCreatedDataException;
import lims.api.common.exception.NoUpdatedDataException;
import lims.api.sy.dao.CommonCodeManageDao;
import lims.api.sy.service.CommonCodeManageService;
import lims.api.sy.vo.CommonCodeVO;
import lims.api.sy.vo.CommonDetailCodeVO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommonCodeManageServiceImpl implements CommonCodeManageService {

	private final CommonCodeManageDao dao;

	@Override
	public List<CommonCodeVO> findUpperCode(CommonCodeVO param) {
		return dao.findUpperCode(param);
	}

	@Override
	public List<CommonDetailCodeVO> findDetailCode(CommonDetailCodeVO param) {
		return dao.findDetailCode(param);
	}

	@Override
	public void upperCodeInsert(CommonCodeVO param, String plntCd) {
		if (param.getAddedRowItems().size() > 0) {
			int result = 0;
			for (CommonCodeVO row : param.getAddedRowItems()) {
				row.setPlntCd(plntCd);
				result += dao.upperCodeInsert(row);
			}
			if (result == 0) {
				throw new NoCreatedDataException();
			}
		}
	}

	@Override
	public void upperCodeUpdate(CommonCodeVO param, String plntCd) {
		if (param.getEditedRowItems().size() > 0) {
			int result = 0;
			for (CommonCodeVO row : param.getEditedRowItems()) {
				result += dao.upperCodeUpdate(row);
			}
			if (result == 0) {
				throw new NoUpdatedDataException();
			}
		}
	}

	@Override
	public void upperDetailCodeInsert(CommonDetailCodeVO param, String plntCd) {
		if (param.getAddedRowItems().size() > 0) {
			int result = 0;
			for (CommonDetailCodeVO row : param.getAddedRowItems()) {
				row.setPlntCd(plntCd);
				result += dao.upperDetailCodeInsert(row);
			}
			if (result == 0) {
				throw new NoCreatedDataException();
			}
		}
	}

	@Override
	public void upperDetailCodeUpdate(CommonDetailCodeVO param, String plntCd) {
		if (param.getEditedRowItems().size() > 0) {
			int result = 0;
			for (CommonDetailCodeVO row : param.getEditedRowItems()) {
				row.setPlntCd(plntCd);
				result += dao.upperDetailCodeUpdate(row);
			}
			if (result == 0) {
				throw new NoUpdatedDataException();
			}
		}
	}
}
