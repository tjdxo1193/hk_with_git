package lims.api.sm.vo;

import lims.api.common.domain.UpdateDetect;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SpecimenManageVO implements UpdateDetect {
    private String plntCd;
    private Integer spcmIdx; // 표준견본 IDX
    private String pitmVer; // 품목 버전
    private String pitmCd; // 품목 코드
    private Integer revNo; // RevNo
    private String enmDt; // 제정 일자
    private String expirDt; // 유효기한
    private String aprUid; // 승인 UID
    private String aprUnm; // 승인 Unm
    private String muft; // 제조원
    private String rmk; // 비고
    private String useYn;

    private String pitmNm;
    private String pitmEn;
    private String pitmTypNm;

    private String hisRevNo;
    private Integer hisSeq;
    private String chgRea;
    private String udtHis;
    private String udtIp;
    private String udtDs;
    private String udtUnm;

    private List<String> formEnmDt; //제정일자
    private List<String> formExpirDt; //유효기한
}
