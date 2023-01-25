package lims.api.ts.vo;

import lims.api.common.domain.UpdateDetect;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SrmMesModalVO implements UpdateDetect {
    private String plntCd; //사업장 코드

    /*마감오더*/
    private String phsOrderTyp;
    private String pdtOrderNo;
    private String orderItm;
    private String lotNo;
    private String finlStt;
    private String crtDs;
    private String ifInfoIdx;

    /*재발방지대책서,SRM성적서,MES포장사양서*/
    private String batchNo;
    private String seq;
    private String phsOrderNo;
    private String makNo;
    private String fileName;
    private String fileId;
    private String fileData;
    private String rptDiv;
    private String picmCd;
    private String picmNm;
    private String ver;
}
