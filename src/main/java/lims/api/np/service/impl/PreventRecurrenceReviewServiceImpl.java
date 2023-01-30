package lims.api.np.service.impl;

import lims.api.np.dao.PreventRecurrenceReviewDao;
import lims.api.np.service.PreventRecurrenceReviewService;
import lims.api.np.vo.PreventRecurrenceReviewVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PreventRecurrenceReviewServiceImpl implements PreventRecurrenceReviewService {

    private final PreventRecurrenceReviewDao dao;


    @Override
    public List<PreventRecurrenceReviewVO> findAll(PreventRecurrenceReviewVO param) {
        return null;
    }
}
