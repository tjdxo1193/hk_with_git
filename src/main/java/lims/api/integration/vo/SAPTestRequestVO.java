package lims.api.integration.vo;

import lims.api.integration.enums.TestRequestType;
import lims.api.util.process.MappingKey;
import lims.api.integration.domain.eai.RevStateful;
import lims.api.integration.model.SAPRequestForTestRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SAPTestRequestVO {

    private RequestHeader requestHeader;
    private List<RequestDetails> requestDetails;

    public static SAPTestRequestVO of(SAPRequestForTestRequest vo) {
        return new ModelMapper().map(vo, SAPTestRequestVO.class);
    }

    @Getter
    @Setter
    public static class RequestHeader extends RevStateful {
        @MappingKey
        private String guid;
        @MappingKey
        private String zqcreqno;
        private String werks;
        private String matnr;
        private String maktx;
        private String charg;
        private String erfmg;
        private String erfme;
        private String lgort;
        private String budat;
        private String bsart;
        private String ebeln;
        private String ebelp;
        private String pstyp;
        private String netpr;
        private String peinh;
        private String waers;
        private String dmbtr;
        private String menge;
        private String meins;
        private String lifnr;
        private String name1;
        private String afnam;
        private String zparcel;
        private String lfdat;
        private String zdeliverytime;
        private String zcoa;
        private String zlotNo;
        private String zaltBomNo;
        private String zvndLotNo;
        private String hsdat;
        private String zstoringEndDate;
        private String zshelfLifeEndDate;
        private String auart;
        private String aufnr;
        private String zsubconno;
        private String zsubconname;
        private String zequnr;
        private String zworker;
        private String mblnr;
        private String mjahr;
        private String zeile;
        private String zcustomer;
        private String zcustomername;
        private TestRequestType zexfield1;
        private String zexfield2; // null이면 정상, 'X'면 검사 취소
        private String zexfield3;
        private String zexfield4;
        private String zexfield5;
    }

    @Getter
    @Setter
    public static class RequestDetails extends RevStateful {
        @MappingKey
        private String guid;
        @MappingKey
        private String zqcreqno;
        @MappingKey
        private String zqcnoBlk;
        private String zexfield1;
        private String zexfield2;
        private String zexfield3;
        private String zexfield4;
        private String zexfield5;
    }

}