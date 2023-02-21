package lims.api.common.vo;

import lims.api.common.enums.UseType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditRecordVO {

    private String plntCd;
    private Integer auditIdx;
    private String menuCd;
    private String httpMtd;
    private String evtUrl;
    private String crtIp;
    private String crtDs;
    private String crtUid;
    private UseType eleSgntYn = UseType.N;
    private String rea;

}