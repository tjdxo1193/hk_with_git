package lims.api.schedule.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StabTestSearchVO {
    private String plntCd;
    private String sbtAnsIdx;
    private String sbtPlnIdx;
    private String ansDt;
}