package lims.api.tp.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "QS_SMP_USE")
public class QsSmpUse {
    @AuditId
    private String plntCd;
    @AuditId
    private Integer smpMngIdx;
    @AuditId
    private Integer useSeq;
    private String smpUseProc;
    private String useSmpVol;
    private String usePps;
    private String useUid;
    private String useDt;
    private String strgPla;
    private Character delYn;
    private String delRea;
    private Integer smpUseAprIdx;
    private String rjtUid;
    private String rjtDs;
    private String rjtRea;
}
