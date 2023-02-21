package lims.api.sy.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class LoginHistoryVO {
    /*  SY_로그인 이력 */
    private String plntCd;
    private Integer acssIdx;
    private Character acssDiv;
    private String acssUid;
    private String acssDs;
    private String acssIp;

    /* SY_사업장*/
    private String plntNm;
    private String plntAdr;
    private Integer ord;
    private Character useYn;
    private String crtIp;
    private String crtDs;
    private String crtUid;
    private String udtIp;
    private String udtDs;
    private String udtUid;

    /* SY_사용자 */
    private String userId;
    private String dptCd;
    private String athCd;
    private String userNm;
    private String userMail;
    private String userTel;
    private String userLognId;
    private String emid;
    private String gradeNm;
    private String titleNm;
    private String ansYn;
    private String clltYn;
    private String revwYn;
    private String aprYn;
    private String pwd;
    private Character lockYn;
    private Integer lognFailCnt;

    /* Alias */
    private List<String> acssDsList = new ArrayList<>();
}
