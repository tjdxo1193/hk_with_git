package lims.api.ms.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestItemPerManageVO {
	// Etc.
	private String crtUserNm;		// 유저 명
	
	// MS_AITM
	private String aitmCd;      // 시험항목 코드
	private String aitmKn;      // 시험항목 국문
	private String aitmEn;      // 시험항목 영문
	private String aitmAbbr;	// 시험항목 약어
	private String crtIp;		// 생성 IP
	private String crtDs;		// 생성 일시
	private String crtUid;      // 생성 UID
	private String udtIp;		// 수정 IP
	private String udtDs;		// 수정 일시
	private String udtUid;      // 수정 UID
	
	// MS_AMITM
	private String amitmCd;		// 시험항목별방법 코드
//	private String aitmCd;		// 시험항목 코드
	private String vriaNm;		// 항목명(VARIANT 국문)
	private String vriaNo;		// VARIANT NO
	private String vriaKn;		// VARIANT 국문
	private String vriaEn;		// VARIANT 영문
	private String rmk;			// 비고
	private String useYn;		// 사용 여부
//	private String crtIp;		// 생성 IP
//	private String crtDs;		// 생성 일시
//	private String crtUid;      // 생성 UID
//	private String udtIp;		// 수정 IP
//	private String udtDs;		// 수정 일시
//	private String udtUid;      // 수정 UID
}
