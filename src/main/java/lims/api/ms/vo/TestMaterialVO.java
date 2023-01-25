package lims.api.ms.vo;

import lims.api.common.domain.UpdateDetect;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestMaterialVO implements UpdateDetect {
    private String plntCd;
    private String ritmCd;
    private String upperRitmTreeCd;
    private String upperRitmTreeNm;
    private String ritmTreeCd;
    private String ritmTreeNm;
    private String ritmKn;
    private String ritmEn;
    private String ritmAbbr;
    private String ritmRmk;
    private String ritmCrgUid;
    private String ritmCrgNm;
    private String ritmUnitCd;
    private String ritmUnitNm;
    private String strgTerms;
    private String strgTermsNm;
    private String strgPla;
    private String strgPlaNm;
    private String mngNo;
    private Integer sfyStok;
    private Integer opnAftExpirTrm;
    private String spec;
    private String casNo;
    private String fomu;
    private String shadeYn;
    private String mtrDedutYn;
    private String poisYn;
    private String dangYn;
    private String crtIp;
    private String crtDs;
    private String crtUid;
    private String udtIp;
    private String udtDs;
    private String udtUid;
    private String useYn;
    private String treeCd;
}