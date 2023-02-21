package lims.api.in.service.impl;

import lims.api.common.exception.NoUpdatedDataException;
import lims.api.common.service.ApproveService;
import lims.api.in.dao.InstHisApprDao;
import lims.api.in.enums.EquipmentHistoryProcess;
import lims.api.in.service.InstHisApprService;
import lims.api.in.vo.InstHisApprVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InstHisApprServiceImpl implements InstHisApprService {

    private final InstHisApprDao dao;
    private final ApproveService approveService;

    @Override
    public List<InstHisApprVO> findAll(InstHisApprVO param) {
        param.setEqmHisProcCd(EquipmentHistoryProcess.APPROVE_REQUEST.getProcessCode());
        return dao.findAll(param);
    }

    @Override
    public List<InstHisApprVO> findByHisSeq(Integer hisSeq) {
        return dao.findByHisSeq(hisSeq);
    }

    @Override
    public void approve(List<InstHisApprVO> list) {
        int result = 0;
        for(InstHisApprVO row : list) {
            approveService.approve(row.getEqmHisAprIdx());
            row.setEqmHisProcCd(EquipmentHistoryProcess.APPROVE_COMPLETE.getProcessCode());
            result += dao.approve(row);
        }

        if(result != list.size()) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void reject(List<InstHisApprVO> list) {
        int result = 0;
        for(InstHisApprVO row : list) {
            row.setEqmHisProcCd(EquipmentHistoryProcess.APPROVE_RETURN.getProcessCode());
            result += dao.reject(row);
        }

        if(result != list.size()) {
            throw new NoUpdatedDataException();
        }
    }
}
