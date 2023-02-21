package lims.api.common.dao;

import lims.api.common.vo.AuditRecordDetailVO;
import lims.api.common.vo.AuditRecordVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuditRecordDao {

    int nextIdx(String plantCode);

    int record(AuditRecordVO param);

    int nextDetailSeq(AuditRecordVO param);

    int recordDetail(AuditRecordDetailVO param);

}