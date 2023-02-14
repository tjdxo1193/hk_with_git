package lims.api.np.service;

import lims.api.np.vo.PreventRecurrenceApprVO;

import java.util.List;

public interface PreventRecurrenceApprService {
    List<PreventRecurrenceApprVO> findAll(PreventRecurrenceApprVO param);

    List<PreventRecurrenceApprVO> findResultItem(PreventRecurrenceApprVO param);

    void approve(PreventRecurrenceApprVO param);
}
