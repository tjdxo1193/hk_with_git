package lims.api.gl.service;

import lims.api.gl.vo.GlassMaterialVO;

import java.util.List;

public interface GlassOpenDisService {
    List<GlassMaterialVO> findAll(GlassMaterialVO param);
    void open(List<GlassMaterialVO> list);
    void disposal(List<GlassMaterialVO> list);
}
