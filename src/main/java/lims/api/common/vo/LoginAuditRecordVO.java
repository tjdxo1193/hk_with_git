package lims.api.common.vo;

import lims.api.common.enums.UseType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginAuditRecordVO {
    private String plntCd;
    private int conectIdx;
    private String conectSe;
    private String conectUid;
    private String conectDt;
    private String conectIp;
    private UseType sso;
}