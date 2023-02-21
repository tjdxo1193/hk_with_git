package lims.api.schedule.vo;

import lims.api.integration.annotation.EnumType;
import lims.api.st.enums.SbtPlnSttProcess;
import lims.api.ts.enums.TestProcess;
import lims.api.ts.enums.TestType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class StabTestVO {
    private String plntCd;
    private Integer sbtAnsIdx;
    private Integer sbtPlnIdx;
    private String ansDt;
    private Integer ansIdx;

    private Integer newAnsIdx;
    private Integer aitmSpecIdx;

    @EnumType(TestProcess.class)
    private String ansProcCd;
    @EnumType(TestType.class)
    private String ansTyp;
    private String ansEdt;
    private String cplRqmDt;
    private String reqDt;
    private LocalDateTime reqDs;

    public void setAitmSpecIdx(Integer aitmSpecIdx) {
        this.aitmSpecIdx = aitmSpecIdx;
    }

    @Getter
    @Builder
    @EqualsAndHashCode
    public static class StabPlanStatus {
        private String plntCd;
        private Integer sbtPlnIdx;
        @EnumType(SbtPlnSttProcess.class)
        private String sbtPlnStt;

        @Setter
        @EnumType(SbtPlnSttProcess.class)
        private String updateSbtPlnStt;
        @Setter
        private String udtUid;
        @Setter
        private String udtIp;
    }
}