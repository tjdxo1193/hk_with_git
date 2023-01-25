package lims.api.auth.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lims.api.auth.domain.Claims;
import lims.api.auth.domain.DecodedToken;
import lims.api.auth.domain.Token;
import lims.api.auth.properties.AuthProperties;
import lims.api.auth.service.TokenProvider;
import lims.api.common.message.MessageUtil;
import lims.api.util.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

@Slf4j
@Component
public class JwtProvider implements TokenProvider {

    private final Algorithm algorithm;
    private final JWTVerifier verifier;
    private final String issuer;
    private final Map<String, Object> header = Map.of(
            "alg", "HS256",
            "typ", "JWT"
    );

    private final AuthProperties properties;

    public JwtProvider(AuthProperties properties) {
        Algorithm algorithm = Algorithm.HMAC256(properties.getSecret());
        String issuer = properties.getIssuer();
        this.algorithm = algorithm;
        this.verifier = JWT.require(algorithm).withIssuer(issuer).build();
        this.issuer = properties.getIssuer();
        this.properties = properties;
    }

    @Override
    public Token generateAccessToken(Map<String, String> customClaims) {
        return new Token(createJwt(accessTokenExpiration(), customClaims));
    }

    @Override
    public Token generateRefreshToken(Map<String, String> customClaims) {
        return new Token(createJwt(refreshTokenExpiration(), customClaims));
    }

    @Override
    public boolean verify(String jwt) {
        try {
            if (jwt == null) {
                return false;
            }
            verifier.verify(jwt);
            return true;
        } catch (JWTVerificationException e) {
            log.error("[{}] invalid json web token. {}", ThreadUtil.getCurrentMethodName(), e.getMessage());
            return false;
        } catch (Exception e) {
            log.error("[{}] {}", ThreadUtil.getCurrentMethodName(), e.getMessage());
            return false;
        }
    }

    public DecodedToken decode(String jwt) {
        try {
            if (jwt == null) {
                throw new IllegalArgumentException(MessageUtil.getMessage("auth.error.notFoundToken"));
            }
            DecodedJWT decodedJWT = JWT.decode(jwt);

            Claims stringMap = new Claims();
            decodedJWT.getClaims().forEach((k, v) -> stringMap.put(k, v.as(String.class)));

            return DecodedToken.builder()
                    .jwt(jwt)
                    .claims(stringMap)
                    .expiresAt(decodedJWT.getExpiresAt())
                    .build();
        } catch (JWTVerificationException e) {
            log.error("[{}] failed decode json web token. {}", ThreadUtil.getCurrentMethodName(), e.getMessage());
            return null;
        } catch (Exception e) {
            log.error("[{}] {}", ThreadUtil.getCurrentMethodName(), e.getMessage());
            throw new RuntimeException(MessageUtil.getMessage("auth.error.default"));
        }
    }

    private String createJwt(Date expiration, Map<String, String> customClaims) {
        JWTCreator.Builder jwtBuilder = JWT.create();

        if (!CollectionUtils.isEmpty(customClaims)) {
            customClaims.forEach(jwtBuilder::withClaim);
        }

        return jwtBuilder
                .withHeader(header)
                .withIssuer(issuer)
                .withIssuedAt(nowDate())
                .withExpiresAt(expiration)
                .sign(algorithm);
    }

    private Date accessTokenExpiration() {
        return properties.getAccessTokenExpire().toDate();
    }

    private Date refreshTokenExpiration() {
        return properties.getRefreshTokenExpire().toDate();
    }

    private Date nowDate() {
        return Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
    }
}