package lims.api.gl.service;

import lims.api.gl.vo.GlassMaterialVO;

import java.util.List;

public interface GlassSearchService {
    List<GlassMaterialVO> findAll(GlassMaterialVO param);
    List<GlassMaterialVO> findAllByRitmEtrIdx(Integer ritmEtrIdx);
}
