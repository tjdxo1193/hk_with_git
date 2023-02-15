package lims.api.schedule.dao;

import lims.api.schedule.vo.StabTestConditionVO;
import lims.api.schedule.vo.StabTestSearchVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StabTestScheduleDao {

    List<StabTestSearchVO> findStabs(StabTestConditionVO param);

}