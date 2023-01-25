package lims.api.sy.vo;

import lims.api.common.domain.UpdateDetect;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CommonDetailCodeVO implements UpdateDetect {
    private String plntCd;
    private String hirCd;
    private String dtlCd;
    private String dtlNm;
    private String dtlEn;
    private String dtlAbbr;
    private String dtlCdOrd;
    private String useYn;
    private String dtlRmk;
    private String extCd1;
    private String extCd2;
    private String extCd3;
    private String crtIp;
    private String crtDs;
    private String crtUid;
    private String udtIp;
    private String udtDs;
    private String udtUid;

    private List<CommonDetailCodeVO> addedRowItems;
    private List<CommonDetailCodeVO> editedRowItems;
}