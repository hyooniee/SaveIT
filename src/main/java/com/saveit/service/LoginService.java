package com.saveit.service;

import com.saveit.vo.Login;


public interface LoginService {
    Login findOrCreateUser(String googleId, String email, String name);   // 메인 로직
    Login findByEmail(String email);                     // 조회만
    void insertUser(String email, String name, String googleId);        // 삽입만
}
