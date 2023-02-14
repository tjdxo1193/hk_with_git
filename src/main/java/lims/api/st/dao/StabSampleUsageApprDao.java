package lims.api.st.dao;

import lims.api.st.vo.StabSampleUsageApprVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StabSampleUsageApprDao {
    List<StabSampleUsageApprVO> find(StabSampleUsageApprVO param);

    int approve(StabSampleUsageApprVO param);

    int reject(StabSampleUsageApprVO param);
}
