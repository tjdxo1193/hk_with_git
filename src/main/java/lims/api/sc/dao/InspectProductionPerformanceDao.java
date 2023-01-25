package lims.api.sc.dao;

import lims.api.sc.vo.InspectProductionPerformanceVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface InspectProductionPerformanceDao {
    List<InspectProductionPerformanceVO> find(InspectProductionPerformanceVO param);
}
