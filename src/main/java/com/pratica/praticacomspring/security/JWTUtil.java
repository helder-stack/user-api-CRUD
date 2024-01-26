package com.pratica.praticacomspring.security;

import com.pratica.praticacomspring.models.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SignatureException;
import java.util.*;


@Service
public class JWTUtil  {
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public String generateToken(User user){
       Date currentDate = new Date();
       Date expirationDate = new Date(currentDate.getTime() + 3 * 60 * 60 * 1000);
       String token = Jwts.builder()
               .setSubject(user.getEmail())
               .setClaims(this.getJWTClaims(user))
               .setExpiration(expirationDate)
               .setIssuedAt(new Date())
               .signWith(Keys.hmacShaKeyFor(SecurityConfig.KEY)).compact();
        return SecurityConfig.PREFIX+" "+token;
    }

    public JWTObject validateToken(String token, String prefix)
        throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException {
        JWTObject object = new JWTObject();
        Claims claims = Jwts.parser().verifyWith(Keys.hmacShaKeyFor(SecurityConfig.KEY)).build().parseSignedClaims(token.replace(SecurityConfig.PREFIX, "").trim()).getBody();
        object.setExpiration(claims.getExpiration());
        object.setIssuedAt(claims.getIssuedAt());
        object.setIssuedAt(claims.getIssuedAt());
        object.setRoles((ArrayList) claims.get("roles"));
        return object;
    }

    private Map<String, Object> getJWTClaims(User user){
        Map<String, Object> claims = new HashMap();
        claims.put("email", user.getEmail());
        claims.put("name", user.getName());
        claims.put("roles", Arrays.asList("ROLE_USER", "ROLE_ADMIN"));
        return claims;
    }
}
