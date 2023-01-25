package lims.api.re.dao;

import lims.api.re.vo.ReagentMaterialVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ReagentSearchDao {
    List<ReagentMaterialVO> findAll(ReagentMaterialVO param);
    List<ReagentMaterialVO> findAllByRitmEtrIdx(Integer ritmEtrIdx);
    List<ReagentMaterialVO> findAllByRitmMngIdx(Integer ritmEtrIdx, Integer ritmMngIdx, String delYn);
}
