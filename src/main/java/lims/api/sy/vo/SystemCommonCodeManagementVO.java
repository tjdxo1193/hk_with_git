package lims.api.sy.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SystemCommonCodeManagementVO {
    /* 상위 코드 */
    private String hirCd;
    private String hirCdNm;
    private String hirUseYn;
    private String hirDesc;
    private int hirCdOrd;

    /* 하위 코드 */
    private String dtlCd;
    private String dtlNm;
    private String extCd1;
    private String extCd2;
    private String dtlEn;
    private String dtlAbbr;
    private String dtlUseYn;
    private int dtlCdOrd;

    /* 공통 */
    private String useYn;
    private String crtId;
    private String crtDate;
    private String udtUid;
    private String udtDs;
}
