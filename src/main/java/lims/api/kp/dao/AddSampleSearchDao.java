package lims.api.kp.dao;

import lims.api.kp.vo.AddSampleSearchVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AddSampleSearchDao {
    List<AddSampleSearchVO> find(AddSampleSearchVO param);
}
