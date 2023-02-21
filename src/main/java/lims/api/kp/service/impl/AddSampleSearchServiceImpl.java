package lims.api.kp.service.impl;

import lims.api.kp.dao.AddSampleSearchDao;
import lims.api.kp.service.AddSampleSearchService;
import lims.api.kp.vo.AddSampleSearchVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddSampleSearchServiceImpl implements AddSampleSearchService {
    private final AddSampleSearchDao dao;

    @Override
    public List<AddSampleSearchVO> find(AddSampleSearchVO param) {
        return dao.find(param);
    }
}
