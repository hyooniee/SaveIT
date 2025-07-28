package com.saveit.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import com.saveit.vo.Login;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET = "saveit-very-secure-and-long-secret-key-should-be-over-256bits"; 
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 2; // 2시간

    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    // 토큰 생성
    public String createToken(Login user) {
        return Jwts.builder()
            .setSubject(user.getEmail())
            .claim("userId", user.getUserId())
            .claim("name", user.getName())
            .claim("googleId", user.getGoogleId())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1일 유효
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }


    // 토큰 검증 및 Claims 반환
    public Claims validateToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            System.out.println(" 만료된 JWT입니다: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println(" 지원하지 않는 JWT입니다: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println(" 잘못된 형식의 JWT입니다: " + e.getMessage());
        } catch (SecurityException | IllegalArgumentException e) {
            System.out.println(" JWT 검증 실패: " + e.getMessage());
        }
        return null;
    }

	public Integer getUserId(String token) {
		Claims claims = validateToken(token);
		if(claims == null) {
			return null;
		}
		return claims.get("userId",Integer.class);
	}
}
