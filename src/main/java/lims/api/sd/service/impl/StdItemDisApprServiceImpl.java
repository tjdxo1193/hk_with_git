package lims.api.sd.service.impl;

import lims.api.common.exception.NoUpdatedDataException;
import lims.api.common.service.ApproveService;
import lims.api.common.vo.ApproveVO;
import lims.api.sd.dao.StdItemDisApprDao;
import lims.api.sd.enums.StandardMaterialProcess;
import lims.api.sd.service.StdItemDisApprService;
import lims.api.sd.vo.StandardMaterialVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StdItemDisApprServiceImpl implements StdItemDisApprService {

    private final StdItemDisApprDao dao;
    private final ApproveService approveService;

    @Override
    public List<StandardMaterialVO> findAll(StandardMaterialVO param) {
        List processCodeList = new ArrayList();
        param.setEtrProcCd(StandardMaterialProcess.APPROVE_COMPLETE.getProcessCode());
        processCodeList.add(StandardMaterialProcess.DISPOSAL_REQUEST_IN_STOCK.getProcessCode());
        processCodeList.add(StandardMaterialProcess.DISPOSAL_REQUEST_IN_OPEN.getProcessCode());
        param.setProcessCodeList(processCodeList);
        return dao.findAll(param);
    }

    @Override
    public void approve(List<StandardMaterialVO> list) {
        int result = 0;
        for(StandardMaterialVO row : list) {
            row.setMngProcCd(StandardMaterialProcess.DISPOSAL.getProcessCode());
            approveService.approve(row.getDpsReqAprIdx());
            result += dao.approve(row);
        }

        if(list.size() != result) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void reject(List<StandardMaterialVO> list) {
        int result = 0;

        for(StandardMaterialVO row : list) {
            if(StandardMaterialProcess.DISPOSAL_REQUEST_IN_STOCK.getProcessCode().equals(row.getMngProcCd())) {
                row.setMngProcCd(StandardMaterialProcess.STOCK.getProcessCode());
            }else {
                row.setMngProcCd(StandardMaterialProcess.OPEN.getProcessCode());
            }

            result += dao.reject(row);
        }

        if(list.size() != result) {
            throw new NoUpdatedDataException();
        }
    }
}
