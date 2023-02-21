package lims.api.sy.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.sy.entity.SyMenu;
import lims.api.sy.vo.MenuVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
@Repository
public interface MenuManageDao {

    List<MenuVO> findAll(MenuVO param);

    @Audit(target = SyMenu.class, label = AuditEvent.Menu.create)
    int insert(MenuVO param);

    @Audit(target = SyMenu.class, label= AuditEvent.Menu.update)
    int update(MenuVO param);

    List<MenuVO> findUpperMenu();
}