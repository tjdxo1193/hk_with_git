package lims.api.common.dao;

import lims.api.sy.vo.MenuVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MenuDao {

    List<MenuVO> findUseMenus();

}