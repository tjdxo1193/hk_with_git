package lims.api.ms.service.impl;

import lims.api.common.exception.NoUpdatedDataException;
import lims.api.common.service.ApproveService;
import lims.api.ms.dao.SpecApprDao;
import lims.api.ms.enums.PItemType;
import lims.api.ms.enums.SpecProgress;
import lims.api.ms.service.SpecApprService;
import lims.api.ms.vo.SpecApprVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpecApprServiceImpl implements SpecApprService {

    private final SpecApprDao dao;

    private final ApproveService approveService;

    @Override
    public List<SpecApprVO> getVersionList(SpecApprVO param) {
        param.setSpecProcCd(SpecProgress.APPROVAL_REQUEST.getCode());
        return dao.getVersionList(param);
    }

    @Override
    public List<SpecApprVO> getAItemList(SpecApprVO param) {
        return dao.getAItmList(param);
    }

    @Override
    public void updateApproval(List<SpecApprVO> param) {
        int result = 0;
        for(SpecApprVO savo : param){
            approveService.approve(savo.getPitmAnsSpecAprIdx());

            savo.setSpecProcCd(SpecProgress.APPROVED.getCode());
            result = dao.updateApproval(savo);
            if (result == 0) {
                throw new NoUpdatedDataException();
            }
        }
    }


    @Override
    public void updateReject(List<SpecApprVO> param) {
        int result = 0;
        for(SpecApprVO mvo : param){
            mvo.setSpecProcCd(SpecProgress.APPROVAL_REJECTION.getCode());
            result = dao.updateReject(mvo);
            if (result == 0) {
                throw new NoUpdatedDataException();
            }
        }
    }
}
