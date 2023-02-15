package lims.api.schedule.vo;

import lims.api.integration.annotation.EnumType;
import lims.api.st.enums.SbtAnsProcess;
import lims.api.st.enums.SbtAnsSttProcess;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StabTestConditionVO {
    @EnumType(SbtAnsSttProcess.class)
    private String sbtAnsStt;

    @EnumType(SbtAnsProcess.class)
    private String sbtAnsProc;
}