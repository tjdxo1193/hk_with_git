package lims.api.sy.vo;

import lims.api.common.domain.UpdateDetect;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CommonCodeVO implements UpdateDetect {
    private String plntCd;
    private String hirCd;
    private String cdDivSu;
    private String hirCdNm;
    private String hirCdOrd;
    private String useYn;
    private String hirDesc;
    private String crtIp;
    private String crtDs;
    private String crtUid;
    private String udtIp;
    private String udtDs;
    private String udtUid;
    private String udtNm;

    private List<CommonCodeVO> addedRowItems;
    private List<CommonCodeVO> editedRowItems;
}
