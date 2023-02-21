package lims.api.schedule.dao;

import lims.api.schedule.vo.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MonitorTestScheduleDao {

    List<MonitorTestSearchVO> findMonitors(MonitorTestConditionVO param);

    List<HolidayVO> findHoliday(HolidayConditionVO param);

    int updateLastCreatedDateInMonitorItem(MonitorTestConditionVO param);

    int createMonitorTestRequest(MonitorTestVO param);

}