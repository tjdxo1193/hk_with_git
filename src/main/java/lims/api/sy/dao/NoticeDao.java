package lims.api.sy.dao;

import lims.api.sy.vo.NoticeVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NoticeDao {
    List<NoticeVO> findAll(NoticeVO vo);
}