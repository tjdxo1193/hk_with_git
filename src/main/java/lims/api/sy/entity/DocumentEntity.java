package lims.api.sy.entity;

import lims.api.common.enums.DeleteType;
import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "EI_NTB_NTC")
public class DocumentEntity {
    @AuditId
    private Integer ntbIdx;
    private String ntbDiv;
    private String ntbTit;
    private String ntbCtt;
    private String popYn;
    private String popTrmStr;
    private String popTrmEnd;
    private String fileIdx;
    private DeleteType delYn;
    private String udtUid;
    private String itcCd;
}