package lims.api.pr.vo;

import lims.api.common.domain.UpdateDetect;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportMasterVO implements UpdateDetect {
    private Integer rptIdx;             // 리포트 IDX
    private String rptDiv;              // ZS024 리포트 구분
    private String rptDivNm;            // 리포트 구분(임의)
    private String rptNm;               // 리포트 명
    private String rptRdPath;           // 리포트 RD 경로
    private String rptRmk;              // 성적서 비고
    private String etcRmk;              // 기타 비고
    private String crtIp;               // 생성 IP
    private String crtDs;               // 생성 일시
    private String crtDt;               // 생성 일자(임시)
    private String crtUid;              // 생성 UID
    private String crtUidNm;            // 생성자(임의)
    private String udtIp;               // 수정 IP
    private String udtDs;               // 수정 일시
    private String udtDt;               // 수정 일자
    private String udtUid;              // 수정 UID
    private String udtUidNm;            // 수정자(임의)
    private String loginUserUid;        // 로그인 UID(임의)
}
