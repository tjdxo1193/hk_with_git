package lims.api.sc.dao;

import lims.api.sc.vo.SearchForStabTestVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SearchForStabTestDao {
    List<SearchForStabTestVO> find(SearchForStabTestVO param);

    List<SearchForStabTestVO> findSpec(SearchForStabTestVO param);
}
