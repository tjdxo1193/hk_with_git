package lims.api.schedule.vo;

import lims.api.common.enums.UseType;
import lims.api.integration.annotation.EnumType;
import lims.api.mt.enums.MonitorTestProcess;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MonitorTestVO {
    private String plntCd;
    private String mitmCd;
    private Integer mitmSpecIdx;
    private String reqDt;
    private String cplRqmDt;
    private String finsReqDt;
    private UseType reqCanlYn;

    @EnumType(MonitorTestProcess.class)
    private String ansProcCd;
}