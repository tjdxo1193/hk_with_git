package lims.api.sy.dao;

import lims.api.sy.vo.LoginHistoryVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface LoginHistoryDao {
    List<LoginHistoryVO> find(LoginHistoryVO param);
}
