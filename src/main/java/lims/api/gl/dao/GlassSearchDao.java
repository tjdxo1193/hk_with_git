package lims.api.gl.dao;
import lims.api.gl.vo.GlassMaterialVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface GlassSearchDao {
    List<GlassMaterialVO> findAll(GlassMaterialVO param);
    List<GlassMaterialVO> findAllByRitmEtrIdx(Integer ritmEtrIdx);
}