package com.example.service.impl;

import com.example.service.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTServiceImpl implements JWTService {
    private static final Logger log = LoggerFactory.getLogger(JWTServiceImpl.class);
    private String secretKey = "";

    @Value("${jwt.expiration}")
    private int expiredTime;

    @Value("${jwt.secret:}")
    private String configuredSecret;

    public JWTServiceImpl() {
        // note: actual secret initialization happens in getSecretOrInit()
    }

    @Override
    public String generateToken(String username) {

        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredTime))
                .and()
                .signWith(getKey())
                .compact();
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    private SecretKey getKey() {
        String effective = getSecretOrInit();
        byte[] bytes = Decoders.BASE64.decode(effective);
        return Keys.hmacShaKeyFor(bytes);
    }

    private String getSecretOrInit() {
        if (secretKey != null && !secretKey.isEmpty()) {
            return secretKey;
        }
        if (configuredSecret != null && !configuredSecret.isEmpty()) {
            secretKey = configuredSecret;
            log.info("Using configured jwt.secret (base64) from application properties");
            return secretKey;
        }
        // Fallback: generate ephemeral secret (tokens will invalidate after restart)
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGenerator.generateKey();
            secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
            log.warn("jwt.secret not configured. Generated ephemeral secret; tokens will be invalid after restart.");
            return secretKey;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
