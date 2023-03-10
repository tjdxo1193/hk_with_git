package lims.api.sy.vo;

import lombok.Getter;
import lombok.Setter;
import spring.audit.type.CommandType;

import java.util.List;

@Getter
@Setter
public class AuditTrailSearchVO {

    private String plntCd;
    private Integer auditIdx;
    private Integer auditSrlno;
    private String menuCd;
    private String menuNm;
    private String evtUrl;
    private String crtUid;
    private String crtIp;
    private String Plant;
    private String rea;
    private CommandType cudDiv;
    private String chgBef;
    private String chgAft;
    private String eleSgntYn;

    private String ip;
    private List<String> searchRange;

    private String selectClauseWithMasters;

}