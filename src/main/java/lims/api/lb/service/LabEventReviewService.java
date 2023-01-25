package lims.api.lb.service;

import lims.api.lb.vo.LabEventReviewVO;

import java.util.List;

public interface LabEventReviewService {
    List<LabEventReviewVO> findAll(LabEventReviewVO dto);
    Integer approveRequest(List<LabEventReviewVO> dto);
    Integer reject(List<LabEventReviewVO> dto);
}
