package com.saveit.filter;

import com.saveit.util.JwtUtil;
import com.saveit.vo.Login;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        // JWT 없음 or 형식이 틀림 → 다음 필터로 넘김
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7); // "Bearer " 이후 토큰 추출

        try {
            Claims claims = jwtUtil.validateToken(token);
            if (claims != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                String email = claims.getSubject(); // sub에 email 저장되어 있음
                String name = (String) claims.get("name");
                String googleId = (String) claims.get("googleId");
                Integer userId = (Integer) claims.get("userId");

                // Login 객체 생성
                Login loginUser = new Login();
                loginUser.setEmail(email);
                loginUser.setName(name);
                loginUser.setGoogleId(googleId);
                if (userId != null) {
                    loginUser.setUserId(userId);
                }

                // 권한 설정
                List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));

                // 인증 객체 생성
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(loginUser, null, authorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // SecurityContext에 등록
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (JwtException e) {
            System.out.println("JWT 인증 실패: " + e.getMessage());
            // 인증 실패 → SecurityContext에 등록하지 않고 필터만 넘김
        }

        filterChain.doFilter(request, response);
    }
}
