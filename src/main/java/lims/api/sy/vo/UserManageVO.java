package lims.api.sy.vo;

import lims.api.common.domain.UpdateDetect;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserManageVO implements UpdateDetect {
    /* SY_사용자 */
    private String userId;
    private String plntCd;
    private String dptCd;
    private String athCd;
    private String userNm;
    private String userMail;
    private String userTel;
    private String userLognId;
    private String emid;
    private String gradeNm;
    private String titleNm;
    private Character ansYn;
    private Character clltYn;
    private Character revwYn;
    private Character aprYn;
    private String pwd;
    private Character useYn;
    private Character lockYn;
    private Integer lognFailCnt;
    private String crtIp;
    private String crtDs;
    private String crtUid;
    private String udtIp;
    private String udtDs;
    private String udtUid;
    private String userNmEn;
    private String userCompTel;
    private Integer ord;
    private Character workStt;

    /* 별칭 */
    private String dptNm;
}