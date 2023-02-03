package com.task.paydaytrade.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

import static com.task.paydaytrade.utility.Constant.JWT_HEADER;
import static com.task.paydaytrade.utility.Constant.JWT_PREFIX;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiry}")
    private long expiry;

    public String generateToken(long userId) {
        final Date now = new Date();
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expiry))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Optional<String> extractToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(JWT_HEADER))
                .filter(header -> header.startsWith(JWT_PREFIX))
                .map(header -> header.substring(JWT_PREFIX.length()));
    }

    public Optional<Jws<Claims>> parseTokenToClaims(String token) {
        return Optional.of(
                Jwts.parser().setSigningKey(secret).parseClaimsJws(token));
    }

    public String getSubjectFromClaims(Jws<Claims> claimsJws) {
        return claimsJws.getBody().getSubject();
    }
}
