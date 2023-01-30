package lims.api.kp.dao;

import lims.api.kp.vo.AddSampleLabelPubVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AddSampleLabelPubDao {
    List<AddSampleLabelPubVO> find(AddSampleLabelPubVO param);
}