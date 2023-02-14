package lims.api.schedule.vo;

import lims.api.integration.annotation.EnumType;
import lims.api.schedule.enums.MonitorCycleUnit;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonitorTestSearchVO {
    private String plntCd;
    private String mitmCd;
    private Integer mitmSpecIdx;
    private String ansStrDt;
    private String finsReqDt;
    private int ansItv;

    @EnumType(MonitorCycleUnit.class)
    private String ansCylDiv;

}