package lims.api.sy.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PasswordConditionValidVO {
    private String newPwd;
    private String oldPwd;
}