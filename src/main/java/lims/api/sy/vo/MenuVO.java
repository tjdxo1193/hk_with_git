package lims.api.sy.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuVO {
    private String menuCd;
    private String hirMenuCd;
    private String menuNm;
    private int menuOrd;
    private String menuDesc;
    private String menuPrtYn;
    private String useYn;
}