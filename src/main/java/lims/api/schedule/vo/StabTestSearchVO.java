package lims.api.schedule.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StabTestSearchVO {
    private String plntCd;
    private Integer sbtAnsIdx;
    private Integer sbtPlnIdx;
    private String ansDt;
    private Integer ansIdx;
}