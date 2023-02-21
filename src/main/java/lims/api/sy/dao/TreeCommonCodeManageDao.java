package lims.api.sy.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.sy.entity.SyCdTree;
import lims.api.sy.vo.TreeCommonCodeManageVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
@Repository
public interface TreeCommonCodeManageDao {

    List<TreeCommonCodeManageVO> findAll(String plntCd);

    List<TreeCommonCodeManageVO> findByCode(TreeCommonCodeManageVO param);

    @Audit(target = SyCdTree.class, label = AuditEvent.CommonCodeTree.create)
    int createNode(TreeCommonCodeManageVO param);

    @Audit(target = SyCdTree.class, label = AuditEvent.CommonCodeTree.update)
    int updateNode(TreeCommonCodeManageVO param);

    String getNextChildCodeByRoot(String rootCode);

    String getNextChildCode(String hirTreeCode);

}