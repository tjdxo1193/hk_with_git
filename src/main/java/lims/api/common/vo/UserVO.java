package lims.api.common.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserVO {
    private String userId;
    private String userLognId;
    private String password;
    private String plntCd;
    private String dptCd;
    private String userNm;
    private Integer lognFailCnt;
    private String lockYn;
    private AuthorityVO authority;
    private boolean superUser = false;

    public UserVO() {}

    public UserVO(String userId) {
        this.userId = userId;
    }
}