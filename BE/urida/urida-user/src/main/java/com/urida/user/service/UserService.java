package com.urida.user.service;

import com.urida.user.dto.LoginDto;
import com.urida.user.dto.RegisterDto;
import com.urida.user.entity.User;

public interface UserService {
    User login(LoginDto loginDto);

    void saveUser(RegisterDto registerDto);

    Boolean checkNickname(String nickname);
}
