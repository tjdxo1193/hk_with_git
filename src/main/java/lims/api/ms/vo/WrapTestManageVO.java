package lims.api.ms.vo;

import lims.api.common.domain.UpdateDetect;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WrapTestManageVO implements UpdateDetect {
	// Etc.
	private String revwUserNm;		// REVW_유저명
	private String rjtUserNm;		// RJT_유저명
	private String crtUserNm;		// CRT_유저명
	private String udtUserNm;		// UDT_유저명
	private int searchedAitmSpecIdx;// 새로 생성된 시험항목 규격 IDX
	private String specProcCdNm;	// 규격서 진행상태 코드 명
	private List<String> sapPrdhaList;	// SAP 리스트(조회 시 제외할)
	private List<WrapTestManageVO> testItemList;
	private List<WrapTestManageVO> deleteTestItemList;
	private String aitmKn;			// 시헝항목 국문
	private String vriaNo;			// 시험항목별방법 Variant No
	private String vriaKn;			// 시헝항목병방법 Variant Kn
	
	// SY_CD_DTL
	private String specProcNm;		// DTL_NM AS SPEC_PROC_NM
	
	// QM_PKGA
	private String plntCd;			// 사업장 코드 				(VARCHAR(5))
	private String pkgaCd;			// 자재계층관리 코드 				(VARCHAR(6))
	private int pkgaVer;			// 자재계층관리 버전 				(NUMBER)
	
	private Integer aitmSpecIdx;	// 시험항목 규격 IDX 			(NUMBER)
	private String pkgaDiv;			// ZS010 자재계층관리 구분 			(VARCHAR(8))
	private String pkgaTypNm;		// 자재계층관리 유형 명 			(VARCHAR(50))
	private String sapPrdha;		// SAP 제품계층구조 			(VARCHAR(36))
	private String specProcCd;		// ZS008 규격서 진행상태 코드 	(VARCHAR(8))
	private String delYn;			// 삭제 여부					(Y/N)
	private String delYnNm;			// 삭제 여부	명
	private String useVerYn;		// 사용 버전 여부				(Y/N)
	private String rvsNo;			// 개정 번호 					(VARCHAR(40))
	private String docNo;			// 문서 번호 					(VARCHAR(40))
	private String rvsDt;			// 개정 일자 					(VARCHAR(10))
	private String enfoDt;			// 시행 일자 					(VARCHAR(10))
	private String rvsReaCd;		// ZU007 개정 사유 코드 		(VARCHAR(8))
	private String rvsCtt;			// 개정 내역 					(VARCHAR(200))
	private String rvsDivPs;		// 개정 구분 PS				(Y/N)
	private String revwUid;			// 검토 UID 					(VARCHAR(5))
	private String revwDs;			// 검토 일시 					(DATE)
	private String revwIp;			// 검토 IP 					(VARCHAR(15))
	private Integer pkgaSpecAprIdx;	// 자재계층관리 규격 승인 IDX		(NUMBER)
	private String rjtUid;			// 반려 UID					(VARCHAR(5))
	private String rjtDs;			// 반려 일시					(DATE)
	private String rjtRea;			// 반려 사유					(VARCHAR(300))
	private String crtIp;			// 생성 IP					(VARCHAR(15))
	private String crtDs;			// 생성 일시					(DATE)
	private String crtUid;			// 생성 UID					(VARCHAR(5))
	private String udtIp;			// 수정 IP					(VARCHAR(15))
	private String udtDs;			// 수정 일시					(DATE)
	private String udtUid;			// 수정 UID					(VARCHAR(5))
	
	// IF_REV_SAP_MATERIAL_MARA
	private Integer idx;			// Idx						(NUMBER)
	private Integer degree;			// Degree					(NUMBER)
	private String matnr;			// 자재번호					(VARCHAR(80))
	private String meins;			// 기본 단위					(VARCHAR(6))
	private String matkl;			// 자재그룹					(VARCHAR(18))
	private String bismt;			// 기존자재번호					(VARCHAR(80))
	private String prdha;			// 제품계층구조					(VARCHAR(36))
	private String spart;			// 제품군						(VARCHAR(4))
	private String mstae;			// 플랜트간 자재 상태			(VARCHAR(4))
	private String wrkst;			// 기본자재					(VARCHAR(96))
	private String zeinr;			// 전표						(VARCHAR(44))
	private String taklv;			// 세금분류					(VARCHAR(2))
	private String bstme;			// 오더단위					(VARCHAR(6))
	private String xchpf;			// 배치관리					(VARCHAR(2))
	private String ekwsl;			// 구매값키					(VARCHAR(8))
	private String mhdhb;			// 총 셸프 라이프				(VARCHAR(100))
	private String zlabno;			// Lab No					(VARCHAR(30))
	private String zprodAbbr;		// 생산약어 - 약호				(VARCHAR(80))
	private String ifInfoIdx;		// ifInfoIdx				(NUMBER)
	
	// QM_PITM_AITM_SPEC
	private int aitmSpecVer;		// 시험항목 규격 버전			(NUMBER)
	
	// QM_PITM_SPEC_AITM
	private int aitmSeq;			// 시험항목 순번				(NUMBER)
	private String aitmRmk;
	private String mkrQty;

	private String ansDptCd;		// 시험 부서 코드				(VARCHAR(5))
	private String amitmCd;			// 시험항목별방법 코드			(VARCHAR(8))
	private Integer aitmOrd;		// 시험항목 정렬순서				(NUMBER)
	private String perspecTxt;		// 허가규격 텍스트				(VARCHAR(50))
	private String owcSpecTxt;		// 자사 규격 텍스트				(VARCHAR(50))
	private String specTxtEn;		// 규격 텍스트 영문				(VARCHAR(50))
	private String specTyp;			// ZS006규격 유형				(VARCHAR(8))
	private String jdgTyp;
	private int perSlvLow;
	private int perSlvUpp;
	private String perSlvDesc;
	private int owcSlvLow;
	private int owcSlvUpp;
	private String owcSlvDesc;
	private String slvJdgCfm;
	private String slvJdgNonCfm;
	private String rstUnitCd;
	private int rstDpnt;
	private String rptPrtSlvVal;
	private String rptPrtItmNm;
	private String rptPrtYn;
	private String ispDptCd;
	private int smpClltQty;
	private String smpClltUnit;
	private int ispDurTm;
	private int eqmUseTm;

	// QM_PITM_SPEC
	private int pitmSpecIdx;		// 품목 규격서 IDX
	private String pitmSpecProcCd;	// 규격서 진행코드
	private String pitmCd;			// 품목 코드
	private int pitmVer;			// 품목 버전
	
	// QM_PITM_INFO
	private String pitmNm;			// 품목 명
	private String pitmEn;			// 품목 영문

	// ApproveVO
	private String aprReqIp;
	private String aprIp;
	private String aprReqUid;
	private String aprUid;
	private String aprReqRea;
	private String aprRea;
	
	// Etc.
	private String loginUserUid;	// 로그인 User Uid
	private String loginUserIp;		// 로그인 User IP

	// pitm_info
	private String pitmTyp;
}
