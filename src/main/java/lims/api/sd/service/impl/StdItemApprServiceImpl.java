package lims.api.sd.service.impl;

import lims.api.common.exception.NoCreatedDataException;
import lims.api.common.exception.NoUpdatedDataException;
import lims.api.common.service.ApproveService;
import lims.api.common.service.UserService;
import lims.api.sd.dao.StdItemApprDao;
import lims.api.sd.enums.StandardMaterialProcess;
import lims.api.sd.service.StdItemApprService;
import lims.api.sd.vo.StandardMaterialVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StdItemApprServiceImpl implements StdItemApprService {

    private final StdItemApprDao dao;
    private final ApproveService approveService;
    private final UserService userService;

    @Override
    public List<StandardMaterialVO> findAll(StandardMaterialVO param) {
        param.setWithDelegateUserIds(userService.getDelegateAssignUserIdsWithMe(param.getAprUid()));
        param.setEtrProcCd(StandardMaterialProcess.APPROVE_REQUEST.getProcessCode());
        return dao.findAll(param);
    }

    @Override
    public void approve(List<StandardMaterialVO> list) {
        int result = 0;

        for(StandardMaterialVO row : list) {
            approveService.approve(row.getEtrReqAprIdx());
            insertManageStandardMaterialsAfterApproved(row);

            row.setEtrProcCd(StandardMaterialProcess.APPROVE_COMPLETE.getProcessCode());
            result += dao.approve(row);
        }

        if(result != list.size()) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void reject(List<StandardMaterialVO> list) {
        int result = 0;

        for(StandardMaterialVO row : list) {
            row.setEtrProcCd(StandardMaterialProcess.APPROVE_RETURN.getProcessCode());
            result += dao.reject(row);
        }

        if(result != list.size()) {
            throw new NoUpdatedDataException();
        }
    }

    public void insertManageStandardMaterialsAfterApproved(StandardMaterialVO param) {
        int result = 0;
        int etrQty = param.getEtrQty();

        for(int i=0; i<etrQty; i++) {
            param.setRitmEtrNo(i+1);
            param.setMngProcCd(StandardMaterialProcess.WAREHOUSING.getProcessCode());
            result += dao.create(param);
        }

        if(etrQty != result) {
            throw new NoCreatedDataException();
        }
    }
}