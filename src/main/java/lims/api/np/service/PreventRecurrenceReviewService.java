package lims.api.np.service;

import lims.api.np.vo.PreventRecurrenceReviewVO;

import java.util.List;

public interface PreventRecurrenceReviewService {
    List<PreventRecurrenceReviewVO> findAll(PreventRecurrenceReviewVO param);

    List<PreventRecurrenceReviewVO> findResultItem(PreventRecurrenceReviewVO param);

    void save(PreventRecurrenceReviewVO param);

    void request(PreventRecurrenceReviewVO param);
}
