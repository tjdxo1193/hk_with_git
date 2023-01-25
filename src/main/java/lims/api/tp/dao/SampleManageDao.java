package lims.api.tp.dao;

import lims.api.tp.vo.SampleManageVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SampleManageDao {
    List<SampleManageVO> find(SampleManageVO param);

    int create(SampleManageVO param);

    int update(SampleManageVO param);

    int delete(SampleManageVO param);

    List<SampleManageVO> findTest(SampleManageVO param);
}
