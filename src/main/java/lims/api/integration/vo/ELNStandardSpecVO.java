package lims.api.integration.vo;

import lims.api.integration.domain.eai.RevStateful;
import lims.api.integration.enums.StandardCategory;
import lims.api.ms.enums.ELNProductDiv;
import lims.api.util.process.KeyGenerator;
import lims.api.util.process.MappingKey;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ELNStandardSpecVO extends RevStateful implements KeyGenerator {

    @EqualsAndHashCode.Exclude
    private String guid;
    @MappingKey
    private String labNo;
    @MappingKey
    private ELNProductDiv prdDiv;
    @MappingKey
    private String amitmCd;
    private String labNoErpCode;
    private String stdText;
    private StandardCategory stdCtg;
    private String displayQty;
    private String pmsStdMin;
    private String pmsStdMax;
    private String comStdMin;
    private String comStdMax;
    private String unit;
    private String rmk;
    @EqualsAndHashCode.Exclude
    private String crtDs;

    @Getter
    @Setter
    public static class MsElnSpec implements KeyGenerator {
        @MappingKey
        private String labNo;
        @MappingKey
        private ELNProductDiv prdDiv;

    }

    @Getter
    @Setter
    public static class MsElnSpecDtl implements KeyGenerator {
        @MappingKey
        private String labNo;
        @MappingKey
        private String prdDiv;
        @MappingKey
        private String amitmCd;
        private String labNoErpCode;
        private String specTxt;
        private String spectyp;
        private Float mkrQty;
        private Float perSlvLow;
        private Float perSlvUpp;
        private Float owcSlvLow;
        private Float owcSlvUpp;
        private String unit;
        private String rm;
    }

    @Getter
    @RequiredArgsConstructor
    public static class DifferentKey {
        private final String labNo;
        private final ELNProductDiv prdDiv;

        public String getPrdDivCode() {
            List<String> codes = prdDiv.getPItemCodes();
            return "'" + String.join("', '", codes) + "'";
        }

        public boolean contains(String labNo) {
            return StringUtils.equals(this.labNo, labNo);
        }
    }

}