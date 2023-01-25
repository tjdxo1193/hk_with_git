package lims.api.an.service.Impl;

import lims.api.an.dao.AnalColSearchDao;
import lims.api.an.service.AnalColSearchService;
import lims.api.an.vo.AnalColMaterialVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalColSearchServiceImpl implements AnalColSearchService {

    private final AnalColSearchDao dao;

    @Override
    public List<AnalColMaterialVO> findAll(AnalColMaterialVO param) {
        return dao.findAll(param);
    }

    @Override
    public List<AnalColMaterialVO> findAllByRitmMngIdx(Integer ritmMngIdx) {
        return dao.findAllByRitmMngIdx(ritmMngIdx);
    }
}
