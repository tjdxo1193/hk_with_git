package lims.api.np.service;

import lims.api.np.vo.PreventRecurrenceReviewVO;

import java.util.List;

public interface PreventRecurrenceReviewService {
    List<PreventRecurrenceReviewVO> findAll(PreventRecurrenceReviewVO param);
}
