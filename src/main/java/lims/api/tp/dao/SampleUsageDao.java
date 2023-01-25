package lims.api.tp.dao;

import lims.api.tp.vo.SampleUsageVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SampleUsageDao {
    List<SampleUsageVO> find(SampleUsageVO param);

    List<SampleUsageVO> findSample(SampleUsageVO param);

    int create(SampleUsageVO param);

    int update(SampleUsageVO param);

    int delete(SampleUsageVO param);

    int requestApprove(SampleUsageVO param);
}
