package lims.api.schedule.dao;

import lims.api.schedule.vo.StabTestConditionVO;
import lims.api.schedule.vo.StabTestSearchVO;
import lims.api.schedule.vo.StabTestSpecAitmVO;
import lims.api.schedule.vo.StabTestVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StabTestScheduleDao {

    List<StabTestSearchVO> findStabs(StabTestConditionVO param);

    int beginStabTest(StabTestConditionVO param);

    String findPlanStatus(StabTestVO param);

    int beginPlanStatus(StabTestVO.StabPlanStatus param);


    List<StabTestSpecAitmVO> findSpecAitmKeyByAmitmCode(StabTestVO param);

    Integer findAitmSpecIdx(StabTestVO param);

    int createTestRequestProcess(StabTestVO param);

    int createTestRequestPitmResult(StabTestSpecAitmVO param);

}