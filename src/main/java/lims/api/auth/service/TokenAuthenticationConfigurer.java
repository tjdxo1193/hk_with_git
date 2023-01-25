package lims.api.auth.service;

import lims.api.auth.domain.SafeAccountAuthentication;
import lims.api.auth.domain.AccountAuthentication;
import lims.api.auth.domain.Claims;
import org.apache.commons.lang3.NotImplementedException;

public interface TokenAuthenticationConfigurer {

    default boolean authenticate(AccountAuthentication authentication) {
        return true;
    };

    default Claims createCustomClaimsOnAuthenticateSuccess(SafeAccountAuthentication authentication) {
        throw new UnsupportedOperationException("[createCustomClaimsOnAuthenticateSuccess] Must be implement TokenAuthenticationConfigurer.");
    };

    default void onLogin(SafeAccountAuthentication authentication) {};

    default void onLoginFailed(SafeAccountAuthentication authentication) {};

    default void onLogout(SafeAccountAuthentication authentication) {};
}