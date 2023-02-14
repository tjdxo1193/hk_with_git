package lims.api.st.dao;

import lims.api.st.vo.StabSampleDisVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StabSampleDisDao {
    List<StabSampleDisVO> find(StabSampleDisVO param);

    int requestDispose(StabSampleDisVO params);

    int requestCancelDispose(StabSampleDisVO params);
}
