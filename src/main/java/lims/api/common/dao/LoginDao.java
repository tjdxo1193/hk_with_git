package lims.api.common.dao;

import lims.api.common.vo.LoginAuditRecordVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface LoginDao {

    List<LoginAuditRecordVO> getLoginAuditList(LoginAuditRecordVO param);

    int insertLoginAudit(LoginAuditRecordVO param);

}