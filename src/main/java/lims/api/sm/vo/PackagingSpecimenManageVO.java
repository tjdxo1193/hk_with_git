package lims.api.sm.vo;

import lims.api.common.domain.UpdateDetect;
import lims.api.common.vo.ApproveVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PackagingSpecimenManageVO implements UpdateDetect {
    private String plntCd;
    private Integer pmSpcmIdx; // 포장재 표준견본 IDX
    private String pkgMtrDiv; // 포장재 구분
    private String pkgMtrDivNm; // 포장재 구분명
    private String pmSpcmNo; // 포장재표준견본 번호
    private String rjtUnm;
    private String rjtDs;
    private String rjtRea;
    private String crtUnm;
    private String crtDs;
    private String pmSpcmProc; //진행상태
    private String pmSpcmProcNm;

    private Integer acsrSeq; //순번
    private String pitmVer; // 품목 버전
    private String pitmCd; // 품목 코드
    private String enmDt; // 제정 일자
    private String expirDt; // 유효기한
    private String maxExpirDt; // 최종유효기한
    private String aprUid; // 승인 UID
    private String aprUnm; // 승인 Unm
    private String muft; // 제조원
    private String rmk; // 비고
    private String delYn;
    private String delAprYn; // 삭제 승인 여부
    private String udtDs;

    private String pitmNm;
    private String pitmEn;
    private String pitmTypNm;
    private String finRcpDt; //최신 접수일자

    private List<String> searchEnmDt; //제정일자
    private List<String> searchExpirDt; //유효기한

    private List<PackagingSpecimenManageVO> addedRowItems;   // 추가된 데이터
    private List<PackagingSpecimenManageVO> editedRowItems;  // 수정된 데이터
    private List<PackagingSpecimenManageVO> removedRowItems; // 삭제된 데이터

    /*승인정보*/
    private ApproveVO approveInfo;
    private Integer pmSpcmDelAprIdx; // 포장재표준견본 삭제 승인 IDX
}
