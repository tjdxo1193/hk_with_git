package lims.api.gl.service.impl;

import lims.api.gl.dao.GlassSearchDao;
import lims.api.gl.service.GlassSearchService;
import lims.api.gl.vo.GlassMaterialVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GlassSearchServiceImpl implements GlassSearchService {

    private final GlassSearchDao dao;

    @Override
    public List<GlassMaterialVO> findAll(GlassMaterialVO param) {
        return dao.findAll(param);
    }

    @Override
    public List<GlassMaterialVO> findAllByRitmEtrIdx(Integer ritmEtrIdx) {
        return dao.findAllByRitmEtrIdx(ritmEtrIdx);
    }
}
