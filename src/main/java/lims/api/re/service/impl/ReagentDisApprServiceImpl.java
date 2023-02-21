package lims.api.re.service.impl;

import lims.api.common.exception.NoUpdatedDataException;
import lims.api.common.service.ApproveService;
import lims.api.common.service.UserService;
import lims.api.common.vo.ApproveVO;
import lims.api.re.dao.ReagentDisApprDao;
import lims.api.re.enums.ReagentMaterialProcess;
import lims.api.re.service.ReagentDisApprService;
import lims.api.re.vo.ReagentMaterialVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReagentDisApprServiceImpl implements ReagentDisApprService {

    private final ReagentDisApprDao dao;
    private final ApproveService approveService;
    private final UserService userService;

    @Override
    public List<ReagentMaterialVO> findAll(ReagentMaterialVO param) {
        List processCodeList = new ArrayList();
        param.setEtrProcCd(ReagentMaterialProcess.APPROVE_COMPLETE.getProcessCode());
        processCodeList.add(ReagentMaterialProcess.DISPOSAL_REQUEST_IN_STOCK.getProcessCode());
        processCodeList.add(ReagentMaterialProcess.DISPOSAL_REQUEST_IN_OPEN.getProcessCode());
        param.setProcessCodeList(processCodeList);
        param.setWithDelegateUserIds(userService.getDelegateAssignUserIdsWithMe(param.getAprUid()));
        return dao.findAll(param);
    }

    @Override
    public void approve(List<ReagentMaterialVO> list) {
        int result = 0;
        for(ReagentMaterialVO row : list) {
            row.setMngProcCd(ReagentMaterialProcess.DISPOSAL.getProcessCode());
            approveService.approve(row.getDpsReqAprIdx());
            result += dao.approve(row);
        }

        if(list.size() != result) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void reject(List<ReagentMaterialVO> list) {
        int result = 0;

        for(ReagentMaterialVO row : list) {
            if(ReagentMaterialProcess.DISPOSAL_REQUEST_IN_STOCK.getProcessCode().equals(row.getMngProcCd())) {
                row.setMngProcCd(ReagentMaterialProcess.STOCK.getProcessCode());
            }else {
                row.setMngProcCd(ReagentMaterialProcess.OPEN.getProcessCode());
            }

            result += dao.reject(row);
        }

        if(list.size() != result) {
            throw new NoUpdatedDataException();
        }
    }
}
