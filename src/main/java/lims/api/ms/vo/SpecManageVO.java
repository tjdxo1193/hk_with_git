package lims.api.ms.vo;

import lims.api.common.domain.UpdateDetect;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class SpecManageVO implements UpdateDetect {
    private String plntCd;
    private Integer aitmSpecIdx;
    private Integer aitmSpecVer;
    private Integer pitmSpecIdx;
    private String pitmCd;
    private String pitmVer;
    private String specProcCd;
    private String delYn;
    private String useVerYn;
    private String rvsNo;
    private String docNo;
    private String rvsDt;
    private String enfoDt;
    private String rvsReaCd;
    private String rvsCtt;
    private String rvsDivPs;
    private String revwUid;
    private String revwDs;
    private String revwIp;
    private String pitmAnsSpecAprIdx;
    private String rjtUid;
    private String rjtDs;
    private String rjtRea;
    private String specProcNm;

    private String crgDptCd;
    private String pitmTyp;
    private String pitmNm;
    private String qpSpecProcCd;
    private String qpSpecProcNm;
    private String pItemTypeCondition;

    private String revwUnm;
    private String rjtUnm;
    private String ansDptNm;
    private String amitmNm;
    private String aitmNm;

    private String vriaKn;
    private String aitmKn;
    private String aitmEn;
    private String aitmAbbr;
    private String aitmCd;
    private String vriaNo;
    private String vriaEn;
    private String rmk;
    private String useYn;
    private String amitmCd;

    private String opsSpecUseVerYn;

    private String pitmTypList;

    private String mkrQty;
    private String aitmRmk;
    private String prdDiv;
    private Integer newAitmSpecIdx;
    private List<SpecManageAitmVO> addedRowItems;
    private List<SpecManageAitmVO> editedRowItems;
    private List<SpecManageAitmVO> removedRowItems;

}
