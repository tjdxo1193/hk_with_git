package lims.api.sc.dao;

import lims.api.sc.vo.SearchForMonitorTestVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SearchForMonitorTestDao {
    List<SearchForMonitorTestVO> find(SearchForMonitorTestVO param);

    List<SearchForMonitorTestVO> findSpec(SearchForMonitorTestVO param);
}
