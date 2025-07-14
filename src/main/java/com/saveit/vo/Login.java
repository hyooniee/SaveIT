package com.saveit.vo;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Data
public class Login implements UserDetails {
    private int userId;
    private String googleId;
    private String email;
    private String name;
    private LocalDateTime createAt;

    //  UserDetails 인터페이스 구현부 

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList(); // 권한 없음
    }

    @Override
    public String getPassword() {
        return null; // 비밀번호 사용 안함
    }

    @Override
    public String getUsername() {
        return email; // 로그인 ID로 email 사용
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정 만료 X
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정 잠김 X
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 비밀번호 만료 X
    }

    @Override
    public boolean isEnabled() {
        return true; // 계정 활성화 상태
    }
}
