package lims.api.sc.dao;

import lims.api.sc.vo.InspectPurchasingPerformanceVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface InspectPurchasingPerformanceDao {
    List<InspectPurchasingPerformanceVO> find(InspectPurchasingPerformanceVO param);

    List<InspectPurchasingPerformanceVO> findDetail(InspectPurchasingPerformanceVO param);
}
