package lims.api.kp.dao;

import lims.api.kp.vo.AddSampleRequestVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AddSampleRequestDao {
    List<AddSampleRequestVO> find(AddSampleRequestVO param);

    int requestApprove(AddSampleRequestVO param);

    int create(AddSampleRequestVO param);

    int update(AddSampleRequestVO param);

    int delete(AddSampleRequestVO param);

    String findLabelCd(AddSampleRequestVO param);
}
