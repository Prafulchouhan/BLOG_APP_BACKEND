package com.server.blogappserver.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Component
public class JwtTokenHelper {
    public static final long JWT_TOKEN_VALIDITY= 5 * 60 * 60;
    private String secret="jwtTokenKey";
    public String getUserNameFromToken(String token){
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpiratinDateFromToken(String token){
        return getClaimFromToken(token,Claims::getExpiration);
    }
    private <T> T   getClaimFromToken(String token, Function<Claims,T> claimResolver) {
        final Claims claims=getAllClaimsFromTokens(token);
        return claimResolver.apply(claims);
    }

    private Claims getAllClaimsFromTokens(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token){
        final Date expiration = getExpiratinDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims=new HashMap<>();
        return doGenerateToken(claims,userDetails.getUsername());
    }
    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() +JWT_TOKEN_VALIDITY+100))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public Boolean validateToken(String token,UserDetails userDetails){
        final String username=getUserNameFromToken(token);
        return (username.equals(userDetails.getUsername())&&!isTokenExpired(token));
    }
}
