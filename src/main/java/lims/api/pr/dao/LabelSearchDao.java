package lims.api.pr.dao;

import lims.api.pr.vo.LabelSearchVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface LabelSearchDao {
    // 라벨출력조회 조회
    List<LabelSearchVO> findAll(LabelSearchVO param);
}
