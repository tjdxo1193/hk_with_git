package lims.api.sy.dao;

import lims.api.sy.vo.ScheduleManageVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ScheduleManageDao {

    List<ScheduleManageVO> getList(ScheduleManageVO param);

}
