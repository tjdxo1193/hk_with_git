package lims.api.in.service.impl;

import lims.api.in.dao.InstSearchDao;
import lims.api.in.service.InstSearchService;
import lims.api.in.vo.InstSearchVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InstSearchServiceImpl implements InstSearchService {
    private final InstSearchDao dao;

    @Override
    public List<InstSearchVO> find(InstSearchVO param) {
        return dao.find(param);
    }

    @Override
    public List<InstSearchVO> findHistory(InstSearchVO param) {
        return dao.findHistory(param);
    }
}
