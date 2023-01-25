package lims.api.sy.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.sy.entity.DocumentEntity;
import lims.api.sy.vo.DocumentVO;
import org.apache.ibatis.annotations.Mapper;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
public interface DocumentDao {

    List<DocumentVO> findAll(DocumentVO param);

    Integer nextIdx();

    @Audit(target = DocumentEntity.class, label = AuditEvent.Document.create)
    int create(DocumentVO param);

    @Audit(target = DocumentEntity.class, label = AuditEvent.Document.update)
    int update(DocumentVO param);

}