package lims.api.lb.dao;

import lims.api.lb.vo.LabEventSearchVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface LabEventSearchDao {
    List<LabEventSearchVO> findAll(LabEventSearchVO dto);
}
