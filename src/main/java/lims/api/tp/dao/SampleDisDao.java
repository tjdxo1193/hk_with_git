package lims.api.tp.dao;

import lims.api.tp.vo.SampleDisVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SampleDisDao {
    List<SampleDisVO> find(SampleDisVO param);

    int requestDis(SampleDisVO param);

    int requestDisCancel(SampleDisVO param);
}
