package lims.api.schedule.vo;

import lims.api.integration.annotation.EnumType;
import lims.api.ms.enums.SpecProgress;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MonitorTestConditionVO {
    private String plntCd;
    private String mitmCd;
    private String finsReqDt;

    @EnumType(SpecProgress.class)
    private String specProcCd;

}