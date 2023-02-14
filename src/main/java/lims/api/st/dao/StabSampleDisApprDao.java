package lims.api.st.dao;

import lims.api.st.vo.StabSampleDisApprVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StabSampleDisApprDao {
    List<StabSampleDisApprVO> find(StabSampleDisApprVO param);

    int approve(StabSampleDisApprVO param);

    int reject(StabSampleDisApprVO param);
}
