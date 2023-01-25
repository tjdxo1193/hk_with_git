package lims.api.sy.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.sy.entity.DocumentEntity;
import lims.api.sy.vo.NoticeManagementVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
@Repository
public interface NoticeManagementDao {
    @Audit(target = DocumentEntity.class, label = AuditEvent.Document.create)
    int updateOfPopYn(NoticeManagementVO vo);


    List<NoticeManagementVO> findAll(NoticeManagementVO vo);

    @Audit(target = DocumentEntity.class, label = AuditEvent.Document.create)
    int update(NoticeManagementVO vo);

    @Audit(target = DocumentEntity.class, label = AuditEvent.Document.create)
    int create(NoticeManagementVO vo);
}