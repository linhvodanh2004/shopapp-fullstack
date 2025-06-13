package com.project.pixelstore.components;

import com.project.pixelstore.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtTokenUtils {
    @Value("${jwt.expiration}")
    private int expiration; // save to environment variable

    @Value("${jwt.secret}")
    private String key;

    public String generateTokenFromUser(User user) throws Exception {
        // Properties in User => Claims in JWT
        Map<String, Object> claims = new HashMap<>();
//        this.generateSecretKey();
        claims.put("phoneNumber", user.getPhoneNumber());

        try {
            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(user.getPhoneNumber())
                    .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000L))
                    .signWith(getKey(), SignatureAlgorithm.HS256)
                    .compact();
        } catch (Exception e) {
            throw new InvalidParamException("Error generate token from user: " + e.getMessage());
        }
    }

    // Get all claims from given token
    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    //  Function<Claims, T> claimsResolver: this function have input param as Claims and return a generic T
    public  <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = this.extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    private String generateSecretKey(){
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        String secretKey = Encoders.BASE64.encode(bytes);
        return secretKey;
    }

    public boolean isTokenExpired(String token) {
        // Retrieve single claim from given token, using generic
        final Date expiration = this.extractClaim(token, Claims::getExpiration);
        return expiration.before(new Date());
    }

    public String getPhoneNumberFromToken(String token) {
        return this.extractClaim(token, Claims::getSubject);
    }
    public boolean validateToken(String token, UserDetails userDetails) {
        final String phoneNumber = this.getPhoneNumberFromToken(token);
        return (phoneNumber.equals(userDetails.getUsername()) && !this.isTokenExpired(token));
        // Check username equals or not, and token expire or not
    }
}
