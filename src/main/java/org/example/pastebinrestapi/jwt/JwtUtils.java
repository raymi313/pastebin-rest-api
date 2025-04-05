package org.example.pastebinrestapi.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.example.pastebinrestapi.entities.UserEntity;

import java.util.Date;

public class JwtUtils {

    private static final String SECRET_KEY = "ha34sf3g78237cg3g27rr3gf27gf32sf23fd34ha34sf3g78237cg3g27rr3gf27gf32";
    private static final long EXPIRATION_TIME = 86400000;

    public static String generateToken(UserEntity user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public static String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public static boolean isTokenValid(String token, UserEntity user) {
        final String username = extractUsername(token);
        return (username.equals(user.getUsername())) && !isTokenExpired(token);
    }

    private static boolean isTokenExpired(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }
}
