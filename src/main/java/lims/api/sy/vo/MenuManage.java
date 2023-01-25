package lims.api.sy.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MenuManage {
    private String menuCd;
    private String hirMenuCd;
    private String menuNm;
    private int menuOrd;
    private String menuDesc;
    private String menuPrtYn;
    private String useYn;

    private List<MenuManage> children;

}