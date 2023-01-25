package lims.api.kp.dao;

import lims.api.kp.vo.AddSampleApprVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AddSampleApprDao {
    List<AddSampleApprVO> find(AddSampleApprVO param);

    int approve(AddSampleApprVO param);

    int reject(AddSampleApprVO param);

    int createSample(AddSampleApprVO param);
}
