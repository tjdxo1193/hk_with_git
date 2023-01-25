package lims.api.tp.dao;

import lims.api.tp.vo.SampleDisApprVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SampleDisApprDao {
    List<SampleDisApprVO> find(SampleDisApprVO param);

    int approve(SampleDisApprVO param);

    int reject(SampleDisApprVO param);
}
