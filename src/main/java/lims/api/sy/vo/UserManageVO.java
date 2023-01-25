package lims.api.sy.vo;

import lims.api.common.domain.UpdateDetect;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserManageVO implements UpdateDetect {
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
    private String pwd;
    private String useYn;
    private String lockYn;
    private String lognFailCnt;
    private String dptNm;
    private String athNm;
    private String ansYn;
    private String clltYn;
    private String revwYn;
    private String aprYn;
}