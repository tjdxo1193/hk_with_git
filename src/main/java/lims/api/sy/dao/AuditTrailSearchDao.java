package lims.api.sy.dao;

import lims.api.sy.vo.AuditTrailSearchVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AuditTrailSearchDao {

    List<Map<String, Object>> findAuditsByPlantCodeAndMenuCode(AuditTrailSearchVO param);

}