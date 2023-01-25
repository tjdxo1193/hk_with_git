package lims.api.ms.vo;

import lims.api.ms.enums.UserDelegate;
import lims.api.ms.enums.UserDelegateAppr;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDelegateVO {

    private String userId;
    private UserDelegate delegateStatus;
    private UserDelegateAppr delegateApprStatus;

}