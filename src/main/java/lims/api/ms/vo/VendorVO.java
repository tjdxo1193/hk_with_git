package lims.api.ms.vo;

import lims.api.common.domain.UpdateDetect;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendorVO implements UpdateDetect {
    private String plntCd;
    private String vdrCd;
    private String vdrDiv;
    private String vdrDivNm;
    private String vdrNm;
    private String vdrCrgNm;
    private String vdrCrgTel;
    private String vdrAdr;
    private String vdrAdrDtl;
    private String delYn;
    private String crtIp;
    private String crtDs;
    private String crtUid;
    private String udtIp;
    private String udtDs;
    private String udtUid;
}
