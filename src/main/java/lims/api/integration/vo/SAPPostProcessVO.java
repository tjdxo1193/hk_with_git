package lims.api.integration.vo;

import lims.api.common.enums.DeleteType;
import lims.api.common.enums.UseType;
import lims.api.integration.enums.MaterialCharCode;
import lims.api.integration.enums.MaterialLang;
import lims.api.integration.enums.MaterialMRP;
import lims.api.integration.enums.TestRequestType;
import lims.api.ms.enums.PItemType;
import lims.api.ms.enums.SpecProgress;
import lombok.*;

public class SAPPostProcessVO {

    public static class Material {

        @Getter
        @Builder
        @ToString
        @EqualsAndHashCode
        public static class PItemKey {
            private String plntCd;
            private String pitmCd;
            private Integer version;
        }

        @Getter
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class PItem {
            private String plntCd;
            private String pitmCd;
            private SpecProgress specProcCd;
            private Integer pitmVer;
            private UseType delYn;
            private UseType useVerYn;
            private String crtIp;
            private String crtUid;
            private String udtIp;
            private String udtUid;
            private String rvsCtt;

            public PItem(PItemKey itemKey) {
                this.plntCd = itemKey.getPlntCd();
                this.pitmCd = itemKey.getPitmCd();
                this.pitmVer = itemKey.getVersion();
            }

            public PItem(PItemKey itemKey, Integer version) {
                this.plntCd = itemKey.getPlntCd();
                this.pitmCd = itemKey.getPitmCd();
                this.pitmVer = version;
            }

            public String getSpecProcCd() {
                return specProcCd.getCode();
            }
        }

        @Getter
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class PItemInfo {
            private String plntCd;
            private String pitmCd;
            private Integer pitmVer;
            private String pitmTyp;
            private Integer ansDurDay;

            public PItemInfo(PItemKey itemKey) {
                this.plntCd = itemKey.getPlntCd();
                this.pitmCd = itemKey.getPitmCd();
                this.pitmVer = itemKey.getVersion();
            }

            public PItemInfo(PItemKey itemKey, Integer version) {
                this.plntCd = itemKey.getPlntCd();
                this.pitmCd = itemKey.getPitmCd();
                this.pitmVer = version;
            }

            public String getMaterialLangKO() {
                return MaterialLang.KO.getValue();
            }

            public String getMaterialLangEN() {
                return MaterialLang.EN.getValue();
            }

            public void setPItemTypByMRPCode(String mrpCode) {
                PItemType type = MaterialMRP.getTypeByMRPCode(mrpCode);
                this.pitmTyp = type == null ? null : type.getCode();
            }
        }

        @Getter
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class PItemInfoSap {
            private String plntCd;
            private String pitmCd;
            private Integer pitmVer;
            private String mtrTyp;
            private String mtrMrp;
            private String labNo;
            private String nomMtr;
            private String brdAbbr;
            private String etrCtnQty;
            private String rmtrSpec;
            private String nbr;
            private String ftnYn;
            private String pcs01;
            private String pcs02;
            private String pcs03;
            private String otcPrd;
            private String dmsEptYn;
            private String pearDiv;
            private String mkrVol;
            private String mkrVolUnit;
            private String etnAnsReq;
            private String ctrptNo;
            private String dioYn;
            private String prbInYn;
            private String prbFeYn;
            private String pnxFeYn;
        }

        @Getter
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class PItemSpec {
            private String plntCd;
            private String pitmCd;
            private Integer pitmVer;
            private Integer pitmSpecIdx;
            private Integer aitmSpecIdx;
            private SpecProgress specProcCd;
            private DeleteType delYn;
            private UseType useVerYn;
            private String crtIp;
            private String crtUid;
            private String udtIp;
            private String udtUid;

            private Integer currentVersion;
            private Integer nextVersion;

            private String sapPrdha;

            private UseType aitmSpecIdxIsNull;

            public String getSpecProcCd() {
                return specProcCd.getCode();
            }

            public PItemSpec(PItemKey itemKey) {
                this.plntCd = itemKey.getPlntCd();
                this.pitmCd = itemKey.getPitmCd();
                this.pitmVer = itemKey.getVersion();
            }

            public PItemSpec(PItemKey itemKey, Integer version) {
                this.plntCd = itemKey.getPlntCd();
                this.pitmCd = itemKey.getPitmCd();
                this.pitmVer = version;
            }
        }
    }

    public static class TestRequest {

        @Getter
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class PItemReq {
            private String amtLoccurr;
            private String phsOrderQty;
            private String phsOrderUnit;
            private String splCd;
            private String splNm;
            private String phsCrgNm;
            private String dlvYn;
            private String vdrCtrtDt;
            private String vdrRsvTm;
            private String vdrRptRcpCrst;
            private String lotNo;
            private String repBomNo;
            private String splLotNo;
            private String makDt;
            private String strgLmt;
            private String useLmt;
            private String pdtOrderTyp;
            private String pdtOrderNo;
            private String itnPrsCompCd;
            private String itnPrsCompNm;
            private String makEqp;
            private String wrkNm;
            private String mtrDocNo;
            private String mtrDocYr;
            private String mtrDocItm;
            private String csmBpCd;
            private String csmNm;
            private TestRequestType addCol1;
            private String addCol2;
            private String addCol3;
            private String addCol4;
            private String addCol5;
            private String udtDs;
            private String revDs;
            private String plntCd;
            private Integer reqIdx;
            private String ispReqNo;
            private String ispReqDt;
            private String mtrCd;
            private String mtrNm;
            private String batchNo;
            private String etrQty;
            private String inpUnit;
            private String savePla;
            private String etrDt;
            private String phsOrderTyp;
            private String phsOrderNo;
            private String phsOrderItm;
            private String itmCtg;
            private String phsUnitPre;
            private String amtUnit;
            private String currCd;
        }

        @Getter
        @Setter
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class PItemReqProcess {
            private String plntCd;
            private Integer ansIdx;
            private Integer reqIdx;
            private UseType reqCanlYn;
            private UseType hldYn;
            private String hldUid;
            private String hldRea;
        }


        @Getter
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class PItemReqNonCfm {
            private String plntCd;
            private Integer reqIdx;
            private Integer nonCfmSeq;
            private String ispReqNo;
            private String ispReqNoBlk;
            private String zexfield1;
            private String zexfield2;
            private String zexfield3;
            private String zexfield4;
            private String zexfield5;
            private Integer ifInfoIdx;
        }
    }


}