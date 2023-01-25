package lims.api.common.vo;

import lims.api.common.enums.CUDType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditRecordDetailVO {

    private String plntCd;
    private Integer auditIdx;
    private Integer auditSrlno;
    private String eventNm;
    private String chgBef;
    private String chgAft;
    private CUDType cudDiv;

}