package lims.api.sy.vo;

import lims.api.common.domain.UpdateDetect;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartmentManageVO implements UpdateDetect {
    private String plntCd;
    private String dptCd;
    private int level;
    private String hirDptCd;
    private String dptNm;
    private String dptAbbr;
    private String rmk;
    private String useYn;
    private Integer ord;
    private Integer seq;
}