package lims.api.an.service.Impl;

import lims.api.an.dao.AnalColDisApprDao;
import lims.api.an.enums.AnalColMaterialProcess;
import lims.api.an.service.AnalColDisApprService;
import lims.api.an.vo.AnalColMaterialVO;
import lims.api.common.exception.NoUpdatedDataException;
import lims.api.common.service.ApproveService;
import lims.api.common.vo.ApproveVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalColDisApprServiceImpl implements AnalColDisApprService {

    private final AnalColDisApprDao dao;
    private final ApproveService approveService;

    @Override
    public List<AnalColMaterialVO> findAll(AnalColMaterialVO param) {
        List processCodeList = new ArrayList();
        param.setEtrProcCd(AnalColMaterialProcess.APPROVE_COMPLETE.getProcessCode());
        processCodeList.add(AnalColMaterialProcess.DISPOSAL_REQUEST_IN_STOCK.getProcessCode());
        processCodeList.add(AnalColMaterialProcess.DISPOSAL_REQUEST_IN_OPEN.getProcessCode());
        param.setProcessCodeList(processCodeList);
        return dao.findAll(param);
    }

    @Override
    public void approve(List<AnalColMaterialVO> list) {
        int result = 0;
        for(AnalColMaterialVO row : list) {
            row.setMngProcCd(AnalColMaterialProcess.DISPOSAL.getProcessCode());
            approveService.approve(row.getEtrReqAprIdx());
            result += dao.approve(row);
        }

        if(list.size() != result) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void reject(List<AnalColMaterialVO> list) {
        int result = 0;

        for(AnalColMaterialVO row : list) {
            row.setMngProcCd(AnalColMaterialProcess.OPEN.getProcessCode());
            result += dao.reject(row);
        }

        if(list.size() != result) {
            throw new NoUpdatedDataException();
        }
    }
}
