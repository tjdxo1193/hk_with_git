package lims.api.integration.vo;

import lims.api.util.process.KeyGenerator;
import lims.api.util.process.MappingKey;
import lims.api.integration.domain.eai.RevStateful;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SAPBomVO extends RevStateful implements KeyGenerator {

    private String parentGuid;
    private String guid;
    private String seq;
    @MappingKey
    private String werks;
    @MappingKey
    private String matnr;
    @MappingKey
    private String stlal;
    @MappingKey
    private String posnr;
    private String maktx;
    private String mtart;
    private String name1;
    private String dispo;
    private String dsnam;
    private String stktx;
    private String bmeng;
    private String bmein;
    private String validFrom;
    private String validTo;
    private String stlnr;
    private String ztext;
    private String lkenz;
    private String postp;
    private String ptext;
    private String idnrk;
    private String idnrkMakt;
    private String potx1;
    private String menge;
    private String meins;
    private String fmnge;
    private String beikz;
    private String stlst;
}