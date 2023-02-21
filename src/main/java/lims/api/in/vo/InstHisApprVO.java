package lims.api.in.vo;

import lims.api.common.domain.UpdateDetect;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstHisApprVO implements UpdateDetect {
    private String plntCd;
    private String eqmCd;
    private Integer hisSeq;
    private String eqmNm;
    private String eqmDiv;
    private String eqmDivNm;
    private String eqmCrst;
    private String eqmCrstNm;
    private String srlNo;
    private String makComp;
    private String splComp;
    private String modNm;
    private String sapAstNo;
    private String eqmHisDiv;
    private String eqmHisDivNm;
    private String eqmHisProcCd;
    private String eqmHisProcNm;
    private Integer eqmHisAprIdx;
    private String rjtUid;
    private String rjtNm;
    private String rjtDs;
    private String rjtRea;
    private String ansDt;
    private String rmk;
    private Integer hisFileIdx;
    private String aprReqDiv;
    private String aprReqUid;
    private String aprReqNm;
    private String aprReqDs;
    private String aprUid;
    private String aprDs;
    private String aprYn;
}
