package lims.api.common.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ClaimsVO {

    private String userId;
    private String loginId;
    private String emId;
    private String plntCd;
    private String dptCd;
    private String userNm;
    private AuthorityVO authority;
    private boolean superUser;

}