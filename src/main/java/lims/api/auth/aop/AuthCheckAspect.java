package lims.api.auth.aop;


import lims.api.auth.annotation.Permit;
import lims.api.auth.exception.UnauthenticatedException;
import lims.api.auth.service.TokenHttpResolver;
import lims.api.auth.service.impl.Authenticator;
import lims.api.auth.service.impl.HttpHelper;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.auth.session.TokenSession;
import lims.api.util.ThreadUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AuthCheckAspect {

    private final TokenHttpResolver tokenHttpResolver;
    private final Authenticator authenticator;
    private final JwtResolver jwtResolver;

    @Before("requestPoint() || getPoint() || postPoint() || putPoint()")
    public void AuthCheck(JoinPoint joinPoint) throws UnauthenticatedException {
        if (HttpHelper.isInternalErrorRequest()) {
            return;
        }

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        if (isPermitTarget(method)) {
            return;
        }

        checkAuthentication();
    }

    private boolean isPermitTarget(Method method) {
        return method.isAnnotationPresent(Permit.class);
    }

    private void checkAuthentication() {
        String sessionKey = null;
        String jwt = null;
        try {
            jwt = tokenHttpResolver.getAccessToken().getJwt();
            sessionKey = jwtResolver.getUsername(jwt);

            boolean isValidToken = authenticator.verify(jwt);
            if (!isValidToken) {
                throw new UnauthenticatedException("auth.error.invalidToken");
            }

            boolean isInfiniteToken = jwtResolver.isExpiresAtInfinitely(jwt);
            if (isInfiniteToken) {
                return;
            }

            boolean isExistsRefreshTokenInSession = TokenSession.exists(getSession(), sessionKey);
            if (!isExistsRefreshTokenInSession) {
                throw new UnauthenticatedException("auth.error.noExistsTokenInSession");
            }
        } catch (UnauthenticatedException e) {
            log.error("[{}] Invalid authorized Access. username: {}, jwt: {}", ThreadUtil.getCurrentMethodName(), sessionKey, jwt);
            throw new UnauthenticatedException(e.getMessageCode());
        } catch (Exception e) {
            log.error("[{}] Invalid authorized Access. username: {}, jwt: {}", ThreadUtil.getCurrentMethodName(), sessionKey, jwt);
            throw new UnauthenticatedException("auth.error.unauthenticated");
        }
    }

    private HttpSession getSession() {
        return HttpHelper.getCurrentSession();
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void requestPoint() {}

    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void getPoint() {}

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void postPoint() {}

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void putPoint() {}

}