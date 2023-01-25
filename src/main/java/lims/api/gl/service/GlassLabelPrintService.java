package lims.api.gl.service;

import lims.api.gl.vo.GlassMaterialVO;

import java.util.List;

public interface GlassLabelPrintService {
    List<GlassMaterialVO> findAll(GlassMaterialVO param);
    void update(GlassMaterialVO param);
}
