package lims.api.integration.vo;

import lims.api.integration.domain.eai.RevStateful;
import lims.api.integration.enums.ProductDiv;
import lims.api.integration.enums.StandardCategory;
import lims.api.util.process.KeyGenerator;
import lims.api.util.process.MappingKey;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ELNStandardSpecVO extends RevStateful implements KeyGenerator {

    @EqualsAndHashCode.Exclude
    private String guid;
    @MappingKey
    private String labNo;
    @MappingKey
    private ProductDiv prdDiv;
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
        private ProductDiv prdDiv;

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

}