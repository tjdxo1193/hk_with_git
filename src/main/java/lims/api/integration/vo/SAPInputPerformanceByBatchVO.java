package lims.api.integration.vo;

import lims.api.integration.domain.eai.RevStateful;
import lims.api.integration.model.SAPRequestForInputPerformanceByBatch;
import lims.api.util.process.KeyGenerator;
import lims.api.util.process.MappingKey;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SAPInputPerformanceByBatchVO {

    private List<InputPerformanceHeader> performanceHeader;
    private List<InputPerformanceDetail> performanceDetails;

    public static SAPInputPerformanceByBatchVO of(SAPRequestForInputPerformanceByBatch vo) {
        return new ModelMapper().map(vo, SAPInputPerformanceByBatchVO.class);
    }
    /**
     * 배치별 투입 실적 - HEADER
     */
    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    public static class InputPerformanceHeader extends RevStateful implements KeyGenerator {
        private String guid;
        @MappingKey
        private String werks;
        @MappingKey
        private String matnr;
        @MappingKey
        private String charg;
        private String lotNo;
        private String maktx;
        private String dispo;
        private String bwart;
        private String erfmg;
        private String erfme;
        private String lgort;
        private String budat;
        private String ebeln;
        private String ebelp;
        private String aufnr;
        private String zexfield1;
        private String zexfield2;
        private String zexfield3;
        private String zexfield4;
        private String zexfield5;
    }

    /**
     * 배치별 투입 실적 - Detail
     */
    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    public static class InputPerformanceDetail extends RevStateful implements KeyGenerator {
        private String guid;
        @MappingKey
        private String werks;
        @MappingKey
        private String matnr;
        @MappingKey
        private String charg;
        @MappingKey
        private String matnrInput;
        @MappingKey
        private String chargInput;
        private String lotNoInput;
        private String maktx;
        private String dispo;
        private String bwart;
        private String erfmg;
        private String erfme;
        private String lgort;
        private String ebeln;
        private String ebelp;
        private String aufnr;
        private String zexfield1;
        private String zexfield2;
        private String zexfield3;
        private String zexfield4;
        private String zexfield5;

        private String lotNo;
    }

}