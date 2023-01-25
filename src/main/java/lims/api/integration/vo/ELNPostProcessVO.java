package lims.api.integration.vo;

import lims.api.common.enums.DeleteType;
import lims.api.common.enums.UseType;
import lims.api.integration.annotation.EnumType;
import lims.api.ms.enums.SpecProgress;
import lims.api.util.process.KeyGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ELNPostProcessVO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PItemSpec implements KeyGenerator {
        private String plntCd;
        private String pitmCd;
        private Integer pitmSpecIdx;
        private Integer pitmVer;
        @EnumType(SpecProgress.class)
        private String specProcCd;
        private DeleteType delYn;
        private UseType useVerYn;

        private String updatedUid;
        private String updatedIp;

        private Integer nextPitmSpecIdx;

        public void setDelYn(DeleteType delYn) {
            this.delYn = delYn;
        }

        public void setPitmSpecIdx(Integer pitmSpecIdx) {
            this.pitmSpecIdx = pitmSpecIdx;
        }

        public void setSpecProcCd(String specProcCd) {
            this.specProcCd = specProcCd;
        }
    }
}