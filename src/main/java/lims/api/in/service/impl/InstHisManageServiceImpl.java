package lims.api.in.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lims.api.common.domain.FileKey;
import lims.api.common.exception.NoCreatedDataException;
import lims.api.common.exception.NoDeletedDataException;
import lims.api.common.exception.NoUpdatedDataException;
import lims.api.common.service.ApproveService;
import lims.api.common.service.FileService;
import lims.api.in.dao.InstHisManageDao;
import lims.api.in.enums.EquipmentHistoryProcess;
import lims.api.in.service.InstHisManageService;
import lims.api.in.vo.InstHisManageVO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InstHisManageServiceImpl implements InstHisManageService {

	private final InstHisManageDao dao;
	private final FileService fileService;
	private final ApproveService approveService;

	@Override
	public List<InstHisManageVO> findAll(InstHisManageVO param) {
		return dao.findAll(param);
	}

	@Override
	public int save(InstHisManageVO param) {
		int hisFileIdx = 0;
		if (param.getAddedFiles().size() > 0 || param.getRemovedFileIds().size() > 0) {
			hisFileIdx = savedFile(param);
		}

		param.setEqmHisProcCd(EquipmentHistoryProcess.TEMP_SAVE.getProcessCode());
		int result = dao.save(param);

		if (result == 0) {
			throw new NoCreatedDataException();
		}

		return hisFileIdx;
	}

	@Override
	public void saveAsList(List<InstHisManageVO> list) {
		int result = 0;
		for (InstHisManageVO row : list) {
			row.setEqmHisProcCd(EquipmentHistoryProcess.TEMP_SAVE.getProcessCode());
			result += dao.save(row);
		}

		if (list.size() != result) {
			throw new NoCreatedDataException();
		}
	}

	@Override
	public int update(InstHisManageVO param) {
		int hisFileIdx = 0;
		if (param.getAddedFiles().size() > 0 || param.getRemovedFileIds().size() > 0) {
			hisFileIdx = savedFile(param);
		}

		int result = dao.update(param);

		if (result == 0) {
			throw new NoUpdatedDataException();
		}

		return hisFileIdx;
	}

	@Override
	public void delete(InstHisManageVO param) {
		int result = dao.delete(param);

		if (result == 0) {
			throw new NoDeletedDataException();
		}
	}

	@Override
	public void requestApprove(InstHisManageVO param) {
		param.getApproveInfo().setAprIdx(param.getEqmHisAprIdx());
		int reqIdx = approveService.requestApprove(param.getApproveInfo());
		param.setEqmHisAprIdx(reqIdx);
		param.setEqmHisProcCd(EquipmentHistoryProcess.APPROVE_REQUEST.getProcessCode());
		int result = dao.requestApprove(param);

		if (result == 0) {
			throw new NoCreatedDataException();
		}
	}

	private int savedFile(InstHisManageVO param) {
		if (param.getHisFileIdx() == null) {
			FileKey fileKey = fileService.save(param.getAddedFiles());
			param.setHisFileIdx(fileKey.getFileIdx());
		} else {
			for (FileKey removedFileId : param.getRemovedFileIds()) {
				fileService.deleteFile(removedFileId);
			}
			fileService.save(param.getAddedFiles(), param.getHisFileIdx());
		}

		return param.getHisFileIdx();
	}
}
