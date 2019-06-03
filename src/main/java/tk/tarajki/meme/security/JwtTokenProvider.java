package tk.tarajki.meme.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class JwtTokenProvider {
    @Value("{security.jwt.token.secret-key}")
    private String secretKey;

    public String createToken(String username, LocalDateTime lastTokenRelease) {
        return JWT.create()
                .withSubject(username)
                .withClaim("lastTokenRelease", lastTokenRelease.toEpochSecond(ZoneOffset.UTC))
                .sign(Algorithm.HMAC512(secretKey));
    }

    public DecodedJWT getTokenPayload(String token) {
        return JWT.require(Algorithm.HMAC512(secretKey))
                .build()
                .verify(token);
    }

    public String getUsernameFromTokenPayload(DecodedJWT payload) {
        return payload.getSubject();
    }

    public LocalDateTime getLastTokenReleaseFromTokenPayload(DecodedJWT payload) {
        Claim date = payload.getClaim("lastTokenRelease");

        return LocalDateTime.ofEpochSecond(date.asLong(), 0, ZoneOffset.UTC);
    }
}
