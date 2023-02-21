package lims.api.sy.vo;

import lims.api.common.domain.UpdateDetect;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlantManageVO implements UpdateDetect {

    private String plntCd;
    private String plntNm;

}