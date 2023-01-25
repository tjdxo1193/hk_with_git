package lims.api.in.dao;

import lims.api.in.vo.InstManageByItemVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface InstManageByItemDao {
    List<InstManageByItemVO> findItem(InstManageByItemVO param);

    List<InstManageByItemVO> findDetail(InstManageByItemVO param);

    int updateInstrument(InstManageByItemVO param);
}
