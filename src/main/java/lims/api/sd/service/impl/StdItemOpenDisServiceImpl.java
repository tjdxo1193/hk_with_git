package lims.api.sd.service.impl;

import lims.api.common.exception.NoUpdatedDataException;
import lims.api.common.service.ApproveService;
import lims.api.sd.dao.StdItemOpenDisDao;
import lims.api.sd.enums.StandardMaterialProcess;
import lims.api.sd.service.StdItemOpenDisService;
import lims.api.sd.vo.StandardMaterialVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StdItemOpenDisServiceImpl implements StdItemOpenDisService {

    private final StdItemOpenDisDao dao;
    private final ApproveService approveService;

    @Override
    public List<StandardMaterialVO> findAll(StandardMaterialVO param) {
        List processCodeList = new ArrayList();
        param.setEtrProcCd(StandardMaterialProcess.APPROVE_COMPLETE.getProcessCode());

        if(StandardMaterialProcess.STOCK.getProcessCode().equals(param.getMngProcCd())) {
            processCodeList.add(StandardMaterialProcess.STOCK.getProcessCode());
            param.setProcessCodeList(processCodeList);
        }

        if(StandardMaterialProcess.OPEN.getProcessCode().equals(param.getMngProcCd())) {
            processCodeList.add(StandardMaterialProcess.STOCK.getProcessCode());
            processCodeList.add(StandardMaterialProcess.OPEN.getProcessCode());
            param.setProcessCodeList(processCodeList);
        }

        return dao.findAll(param);
    }

    @Override
    public void open(List<StandardMaterialVO> list) {
        int result = 0;
        for(StandardMaterialVO row : list) {
            row.setMngProcCd(StandardMaterialProcess.OPEN.getProcessCode());
            result += dao.open(row);
        }

        if(list.size() != result) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void requestDisposal(List<StandardMaterialVO> list) {
        int result = 0;

        for(StandardMaterialVO row : list) {
            row.getApproveInfo().setAprIdx(row.getDpsReqAprIdx());
            int reqIdx = approveService.requestApprove(row.getApproveInfo());
            row.setDpsReqAprIdx(reqIdx);

            if(StandardMaterialProcess.OPEN.getProcessCode().equals(row.getMngProcCd())) {
                row.setMngProcCd(StandardMaterialProcess.DISPOSAL_REQUEST_IN_OPEN.getProcessCode());
            }else {
                row.setMngProcCd(StandardMaterialProcess.DISPOSAL_REQUEST_IN_STOCK.getProcessCode());
            }

            result += dao.requestDisposal(row);
        }

        if(list.size() != result) {
            throw new NoUpdatedDataException();
        }
    }
}