package lims.api.common.dao;

import lims.api.common.vo.ErrorVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ErrorDao {

    Long nextIdx();

    int create(ErrorVO vo);

}