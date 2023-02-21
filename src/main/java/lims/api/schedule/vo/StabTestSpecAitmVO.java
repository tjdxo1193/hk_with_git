package lims.api.schedule.vo;

import lims.api.integration.annotation.EnumType;
import lims.api.ts.enums.TestProcess;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StabTestSpecAitmVO {
    private String plntCd;
    private Integer ansIdx;
    private Integer aitmSpecIdx;
    @EnumType(TestProcess.class)
    private String rstProcCd;
}