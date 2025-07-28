package com.saveit.mapper;

import com.saveit.vo.Login;
import org.apache.ibatis.annotations.Param;


public interface LoginMapper {
    Login findByEmail(@Param("email") String email);

    void insertUser(@Param("googleId") String googleId,
                    @Param("email") String email,
                    @Param("name") String name);
}

