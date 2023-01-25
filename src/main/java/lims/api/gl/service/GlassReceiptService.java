package lims.api.gl.service;

import lims.api.gl.vo.GlassMaterialVO;

import java.util.List;

public interface GlassReceiptService {
    List<GlassMaterialVO> findAll(GlassMaterialVO param);
    int create(GlassMaterialVO param);
    void update(GlassMaterialVO param);
    void delete(GlassMaterialVO param);
    int savedFile(GlassMaterialVO param);
}
