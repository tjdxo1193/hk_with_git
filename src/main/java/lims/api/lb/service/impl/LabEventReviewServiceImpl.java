package lims.api.lb.service.impl;

import lims.api.common.service.ApproveService;
import lims.api.lb.dao.LabEventReviewDao;
import lims.api.lb.service.LabEventReviewService;
import lims.api.lb.vo.LabEventReviewVO;
import lims.api.ms.enums.SpecProgress;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LabEventReviewServiceImpl implements LabEventReviewService {

    private final LabEventReviewDao labEventReviewDao;
    private final ApproveService approveService;

    @Override
    public List<LabEventReviewVO> findAll(LabEventReviewVO dto) {
        return labEventReviewDao.findAll(dto);
    }

    @Override
    public Integer approveRequest(List<LabEventReviewVO> dto) {
        int result = 0;
        for(LabEventReviewVO item : dto) {
            int labEvtAprIdx = this.requestApprove(item);
            item.setLabEvtAprIdx(labEvtAprIdx);
            result += labEventReviewDao.approveRequest(item);
        }

        return result;
    }

    @Override
    public Integer reject(List<LabEventReviewVO> dto) {
        int result = 0;
        for(LabEventReviewVO item : dto) {
            result += labEventReviewDao.reject(item);
        }

        return result;
    }

    public int requestApprove(LabEventReviewVO param) {
        param.getApproveInfo().setAprIdx(param.getLabEvtAprIdx());
        param.getApproveInfo().setAprReqDiv(SpecProgress.APPROVAL_REQUEST.getCode());
        return approveService.requestApprove(param.getApproveInfo());
    }
}
