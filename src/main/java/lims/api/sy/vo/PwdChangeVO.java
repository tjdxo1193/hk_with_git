package lims.api.sy.vo;

import lims.api.common.domain.UpdateDetect;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PwdChangeVO implements UpdateDetect {
    private String plntCd;
    private String plntNm;
    private String userId;
    private String userNm;
    private String userLognId;
    private String dptCd;
    private String dptNm;
    private String athCd;
    private String athNm;
    private String titleNm;
    private String gradeNm;
    private String emid;
    private String userTel;
    private String userMail;
    private String pwd;
    private String newPwd;
    private String newPwdConfirm;
    private String message;
}
