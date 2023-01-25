package lims.api.in.dao;

import lims.api.in.vo.InstSearchVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface InstSearchDao {
    List<InstSearchVO> find(InstSearchVO param);

    List<InstSearchVO> findHistory(InstSearchVO param);
}
