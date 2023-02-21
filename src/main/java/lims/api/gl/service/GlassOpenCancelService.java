package lims.api.gl.service;

import lims.api.gl.vo.GlassMaterialVO;

import java.util.List;

public interface GlassOpenCancelService {
    List<GlassMaterialVO> findAll(GlassMaterialVO param);
    void cancel(List<GlassMaterialVO> list);
}
