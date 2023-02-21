package lims.api.sm.vo;

import lims.api.common.domain.UpdateDetect;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProcessSpecimenManageVO implements UpdateDetect {
    private String plntCd;
    private Integer psSpcmIdx; // 공정 표준견본 IDX
    private String pitmVer; // 품목 버전
    private String pitmCd; // 품목 코드
    private Integer pdtNo; // 생산번호
    private String enmDt; // 제정 일자
    private String expirDt; // 유효기한
    private String aprUid; // 승인 UID
    private String aprUnm; // 승인 Unm
    private String muft; // 제조원
    private String rmk; // 비고
    private String useYn;
    private String spcmNo;

    private String pitmNm;
    private String pitmEn;
    private String pitmTypNm;

    private List<String> searchEnmDt; //제정일자
    private List<String> searchExpirDt; //유효기한
}
