package lims.api.an.dao;

import lims.api.an.vo.AnalColMaterialVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AnalColSearchDao {
    List<AnalColMaterialVO> findAll(AnalColMaterialVO param);
    List<AnalColMaterialVO> findAllByRitmMngIdx(Integer ritmMngIdx);
}
