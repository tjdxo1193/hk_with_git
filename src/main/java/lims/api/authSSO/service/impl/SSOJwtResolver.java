package lims.api.authSSO.service.impl;

import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lims.api.auth.domain.Claims;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.Authenticator;
import lims.api.authSSO.domain.SSOProperty;
import lims.api.authSSO.domain.SSOTokenClaims;
import lims.api.util.ThreadUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Base64;
import java.util.HashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class SSOJwtResolver {

    private final SSOProperty ssoProperty;
    private final SSOTokenClaims tokenClaims;

    public Claims decode(String jwt) {
        Jws<io.jsonwebtoken.Claims> claimsJws = Jwts.parser()
                .setSigningKey(getPublicKey())
                .parseClaimsJws(jwt);
        assertExistsSSOJws(claimsJws);
        return tokenClaims.create(new HashMap<>(claimsJws.getBody()));
    }

    public String getLoginId(String jwt) {
        return decode(jwt).get(SSOTokenClaims.CLAIM_NAME_LOGIN_ID);
    }

    private PublicKey getPublicKey() {
        return getCertificate().getPublicKey();
    }

    private Certificate getCertificate() {
        try {
            return CertificateFactory.getInstance("X.509").generateCertificate(getSSOCertKeyInputStream());
        } catch (CertificateException e) {
            log.error("[{}] Failed generate certificate sso cert key.", ThreadUtil.getCurrentMethodName());
            throw new RuntimeException(e);
        }
    }

    private InputStream getSSOCertKeyInputStream() {
        byte[] certDer = Base64.getDecoder().decode(ssoProperty.getSSOCertKey());
        return new ByteArrayInputStream(certDer);
    }

    private void assertExistsSSOJws(Jws<io.jsonwebtoken.Claims> claimsJws) {
        if (claimsJws == null) {
            throw new RuntimeException("No exists claims in SSO Jws.");
        }
    }

}