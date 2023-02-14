package lims.api.st.dao;

import lims.api.st.vo.StabSampleUsageVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StabSampleUsageDao {
    List<StabSampleUsageVO> find(StabSampleUsageVO param);

    int requestApproveUse(StabSampleUsageVO param);

    int requestCancelUse(StabSampleUsageVO param);

    int create(StabSampleUsageVO param);

    int update(StabSampleUsageVO param);

    int delete(StabSampleUsageVO param);

    List<StabSampleUsageVO> findSample(StabSampleUsageVO param);
}
