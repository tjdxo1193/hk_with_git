package lims.api.re.service.impl;

import lims.api.common.exception.NoUpdatedDataException;
import lims.api.common.service.ApproveService;
import lims.api.common.vo.ApproveVO;
import lims.api.re.dao.ReagentOpenDisDao;
import lims.api.re.enums.ReagentMaterialProcess;
import lims.api.re.service.ReagentOpenDisService;
import lims.api.re.vo.ReagentMaterialVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReagentOpenDisServiceImpl implements ReagentOpenDisService {

    private final ReagentOpenDisDao dao;
    private final ApproveService approveService;

    @Override
    public List<ReagentMaterialVO> findAll(ReagentMaterialVO param) {
        List processCodeList = new ArrayList();
        param.setEtrProcCd(ReagentMaterialProcess.APPROVE_COMPLETE.getProcessCode());

        if(ReagentMaterialProcess.STOCK.getProcessCode().equals(param.getMngProcCd())) {
            processCodeList.add(ReagentMaterialProcess.STOCK.getProcessCode());
            param.setProcessCodeList(processCodeList);
        }

        if(ReagentMaterialProcess.OPEN.getProcessCode().equals(param.getMngProcCd())) {
            processCodeList.add(ReagentMaterialProcess.STOCK.getProcessCode());
            processCodeList.add(ReagentMaterialProcess.OPEN.getProcessCode());
            param.setProcessCodeList(processCodeList);
        }

        return dao.findAll(param);
    }

    @Override
    public void open(List<ReagentMaterialVO> list) {
        int result = 0;
        for(ReagentMaterialVO row : list) {
            row.setMngProcCd(ReagentMaterialProcess.OPEN.getProcessCode());
            result += dao.open(row);
        }

        if(list.size() != result) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void requestDisposal(List<ReagentMaterialVO> list) {
        int result = 0;

        for(ReagentMaterialVO row : list) {
            row.getApproveInfo().setAprIdx(row.getDpsReqAprIdx());
            int reqIdx = approveService.requestApprove(row.getApproveInfo());
            row.setDpsReqAprIdx(reqIdx);
            if(ReagentMaterialProcess.OPEN.getProcessCode().equals(row.getMngProcCd())) {
                row.setMngProcCd(ReagentMaterialProcess.DISPOSAL_REQUEST_IN_OPEN.getProcessCode());
            }else {
                row.setMngProcCd(ReagentMaterialProcess.DISPOSAL_REQUEST_IN_STOCK.getProcessCode());
            }

            result += dao.requestDisposal(row);
        }

        if(list.size() != result) {
            throw new NoUpdatedDataException();
        }
    }
}