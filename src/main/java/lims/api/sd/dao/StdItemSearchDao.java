package lims.api.sd.dao;

import lims.api.sd.vo.StandardMaterialVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StdItemSearchDao {
    List<StandardMaterialVO> findAll(StandardMaterialVO param);
    List<StandardMaterialVO> findAllByRitmEtrIdx(Integer ritmEtrIdx);
    List<StandardMaterialVO> findAllByRitmMngIdx(Integer ritmEtrIdx, Integer ritmMngIdx, String delYn);
}
