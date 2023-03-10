package lims.api.common.domain;

import com.google.gson.Gson;
import lims.api.auth.domain.Claims;
import lims.api.common.vo.ClaimsVO;
import org.springframework.stereotype.Component;

@Component
public class TokenClaims {

    public static final String CLAIM_NAME_USER_ID = "userId";
    public static final String CLAIM_NAME_LOGIN_ID = "loginId";
    public static final String CLAIM_NAME_EM_ID = "emId";
    public static final String CLAIM_NAME_PLANT_CODE = "plntCd";
    public static final String CLAIM_NAME_DPT_CODE = "dptCd";
    public static final String CLAIM_NAME_USER_NM = "userNm";
    public static final String CLAIM_NAME_AUTHORITY = "authority";
    public static final String CLAIM_NAME_SUPER_USER = "isSuperUser";

    public Claims create(ClaimsVO claimsVO) {
        Claims claims = new Claims();
        claims.put(CLAIM_NAME_USER_ID, claimsVO.getUserId());
        claims.put(CLAIM_NAME_LOGIN_ID, claimsVO.getLoginId());
        claims.put(CLAIM_NAME_EM_ID, claimsVO.getEmId());
        claims.put(CLAIM_NAME_PLANT_CODE, claimsVO.getPlntCd());
        claims.put(CLAIM_NAME_DPT_CODE, claimsVO.getDptCd());
        claims.put(CLAIM_NAME_USER_NM, claimsVO.getUserNm());
        claims.put(CLAIM_NAME_AUTHORITY, toJson(claimsVO.getAuthority()));
        claims.put(CLAIM_NAME_SUPER_USER, String.valueOf(claimsVO.isSuperUser()));
        return claims;
    }

    private String toJson(Object arg) {
        return new Gson().toJson(arg);
    }

}