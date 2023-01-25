package lims.api.common.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.common.entity.FileEntity;
import lims.api.common.domain.FileKey;
import lims.api.common.vo.FileVO;
import org.apache.ibatis.annotations.Mapper;
import spring.audit.annotation.Audit;
import spring.audit.type.CommandType;

import java.util.List;

@Mapper
public interface FileDao {

    FileVO findOne(FileVO param);

    List<FileVO> findAll(FileVO param);

    @Audit(target = FileEntity.class, label = AuditEvent.File.create)
    int save(FileVO param);

    @Audit(target = FileEntity.class, label = AuditEvent.File.delete, commandType = CommandType.DELETE)
    int deleteOne(FileVO param);

    @Audit(target = FileEntity.class, label = AuditEvent.File.deleteAll, commandType = CommandType.DELETE)
    int deleteAll(FileVO param);

    Integer nextIdx(String plntCd);

    Integer nextSeq(FileVO param);

}