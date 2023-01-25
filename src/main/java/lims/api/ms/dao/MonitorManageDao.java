package lims.api.ms.dao;

import lims.api.ms.vo.MonitorManageVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MonitorManageDao {
    List<MonitorManageVO> find(MonitorManageVO param);
    int insert(MonitorManageVO param);
    int update(MonitorManageVO param);
    int delete(MonitorManageVO param);
}
