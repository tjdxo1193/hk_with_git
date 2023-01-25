package lims.api.ms.vo;

import lims.api.common.domain.UpdateDetect;
import lims.api.ms.enums.UserDelegate;
import lims.api.ms.enums.UserDelegateAppr;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class StaffJobDelegationVO implements UpdateDetect {
    private String userId;      // 사용자 UID
    private Integer dlgSeq;      // 업무 위임 순번
    private String dlgUid;      // 대리자 UID
    private String dlgDt;       // 업무 위임 일자
    private String gbkDt;       // 재실 일자
    private String dlgRea;      // 업무 위임 사유
    private String dlgProcCd;   // 업무 위임 진행 상태 코드
    private Integer dlgAprReqIdx;        // 업무 승인 요청 IDX
    private String rjtUid;      // 반려자 UID
    private String rjtDs;       // 반려 일시
    private String rjtRea;      // 반려 사유
    private String dlgSttCd;        // 업무 위임 상태 코드

    /* ApproveVO */
    private String plntCd;      // 사업장 코드
    private String aprReqDiv;       // S005 승인 요청 구분
    private String aprReqUid;       // 승인 요청 UID
    private String aprReqDs;        // 승인 요청 일시
    private String aprReqIp;        // 승인 요청 IP
    private String aprReqRea;       // 승인 요청 사유
    private String aprUid;      // 승인자 UID
    private String aprDs;       // 승인 일시
    private String aprIp;       // 승인 IP
    private String aprRea;      // 승인 사유
    private String aprYn;       // 승인 여부

    /* Alias */
    private String userNm;      // 사용자 이름
    private String userLognId;      // 사용자 로그인 아이디
    private String dlgNm;       // 대리자 이름
    private String dlgLognId;       // 대리자 로그인 아이디
    private String dlgProcNm;       // 업무 위임 진행 상태 명칭
    private String dlgSttNm;    // 재실 여부
    private String rjtYn;       // 반려 여부
    private List<String> dlgDtList = new ArrayList<>();     // 재실 일자 조회
    private UserDelegate processCode;
    private UserDelegateAppr apprProcessCode;
    private List<UserDelegate> processCodeList;
    private List<UserDelegateAppr> apprProcessCodeList;
    
    /* Etc. */
    private String rjtDt;       // 반려 일자
}
