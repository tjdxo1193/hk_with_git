package lims.api.integration.vo;

import lims.api.integration.annotation.EnumType;
import lims.api.integration.domain.eai.RevStateful;
import lims.api.integration.enums.SAPPItemType;
import lims.api.integration.model.SAPRequestForMaterial;
import lims.api.util.process.KeyGenerator;
import lims.api.util.process.MappingKey;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SAPMaterialVO {

    private List<Mara> mara = new ArrayList<>();
    private List<Marc> marc = new ArrayList<>();
    private List<Mvke> mvke = new ArrayList<>();
    private List<Zmdv> zmdv = new ArrayList<>();
    private List<Makt> makt = new ArrayList<>();

    public static SAPMaterialVO of(SAPRequestForMaterial vo) {
        return new ModelMapper().map(vo, SAPMaterialVO.class);
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    public static class Mara extends RevStateful implements KeyGenerator {
        @MappingKey
        private String matnr;
        @EnumType(SAPPItemType.class)
        private String mtart;
        private String meins;
        private String matkl;
        private String bismt;
        private String prdha;
        private String spart;
        private String mstae;
        private String wrkst;
        private String zeinr;
        private String taklv;
        private String bstme;
        private String xchpf;
        private String ekwsl;
        private String mhdhb;
        private String zlabno;
        private String zprodAbbr;

        private boolean changedLabNo = false;
        private boolean changedELNStandard = false;

        public void markingChangeLabNo() {
            this.changedLabNo = true;
        }

        public void markingFromELNStandard() {
            this.changedELNStandard = true;
        }
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    public static class Marc extends RevStateful implements KeyGenerator {
        @MappingKey
        private String matnr;
        @MappingKey
        private String werks;
        private String ladgr;
        private String mmsta;
        private String ekgrp;
        private String insmk;
        private String dismm;
        private String fxhor;
        private String dispo;
        private String disls;
        private String bstrf;
        private String bstmi;
        private String beskz;
        private String sobsl;
        private String lgpro;
        private String lgfsb;
        private String plifz;
        private String webaz;
        private String elslo;
        private String strgr;
        private String mtvfp;
        private String fevor;
        private String kzech;
        private String awsls;
        private String prctr;
        private String losgr;
        private String sobsk;
        private String fvidk;
        private String zfirstGiDate;

    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    public static class Mvke extends RevStateful implements KeyGenerator {
        @MappingKey
        private String matnr;
        @MappingKey
        private String vkorg;
        @MappingKey
        private String vtweg;
        private String dwerk;
        private String aumng;
        private String ktgrm;

    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    public static class Zmdv extends RevStateful implements KeyGenerator {
        @MappingKey
        private String matnr;
        @MappingKey
        private String classType;
        @MappingKey
        private String clazz;
        @MappingKey
        private String charCode;
        @MappingKey
        private String charDataTyp;
        private String charValChar;
        private String charValNum;

    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    public static class Makt extends RevStateful implements KeyGenerator {
        @MappingKey
        private String matnr;
        @MappingKey
        private String spras;
        private String maktx;

    }

}