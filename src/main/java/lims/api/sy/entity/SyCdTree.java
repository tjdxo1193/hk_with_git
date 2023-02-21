package lims.api.sy.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "SY_CD_TREE")
public class SyCdTree {

    @AuditId
    private String plntCd;
    @AuditId
    private String treeCd;
    private String hirTreeCd;
    private String treeNm;
    private String treeEn;
    private String treeAbbr;
    private Integer treeCdOrd;
    private String useYn;
    private String treeRmk;
    private Integer deptLmt;
    private String crtIp;
    private String crtDs;
    private String crtUid;
    private String udtIp;
    private String udtDs;
    private String udtUid;

}