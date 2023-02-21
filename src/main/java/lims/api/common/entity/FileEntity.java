package lims.api.common.entity;

import lims.api.common.enums.DeleteType;
import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "SY_FILE_INFO")
public class FileEntity {
    @AuditId
    private String plntCd;
    @AuditId
    private Integer fileIdx;
    @AuditId
    private Integer fileSrlno;
    private String fileNm;
    private Integer ord;
    private Integer fileSize;
    private DeleteType delYn;
    private String fileTyp;

}