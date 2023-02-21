package lims.api.sc.dao;

import lims.api.sc.vo.SearchForTestByStatusVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SearchForTestByStatusDao {
    List<SearchForTestByStatusVO> find(SearchForTestByStatusVO param);

    List<SearchForTestByStatusVO> findSpec(SearchForTestByStatusVO param);
}
