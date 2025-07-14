package com.saveit.service.impl;

import com.saveit.mapper.LoginMapper;
import com.saveit.service.LoginService;
import com.saveit.vo.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final LoginMapper loginMapper;

    @Override
    public Login findOrCreateUser(String googleId, String email, String name) {
        System.out.println("email2222: " + email);
        Login user = loginMapper.findByEmail(email);
        System.out.println("user: " + user);

        if (user == null) {
            loginMapper.insertUser(googleId, email, name);
            user = loginMapper.findByEmail(email);         
        }
        return user;
    }

    @Override
    public Login findByEmail(String email) {
        return loginMapper.findByEmail(email);
    }

    @Override
    public void insertUser(String googleId,String email, String name) {
        loginMapper.insertUser(googleId, email, name);
    }
}
