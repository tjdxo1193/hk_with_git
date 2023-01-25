package lims.api.tp.dao;

import lims.api.tp.vo.SampleUsageApprVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SampleUsageApprDao {
    List<SampleUsageApprVO> find(SampleUsageApprVO param);

    int approve(SampleUsageApprVO param);

    int reject(SampleUsageApprVO param);
}
