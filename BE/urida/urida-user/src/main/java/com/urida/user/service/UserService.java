package com.urida.user.service;

import com.urida.user.dto.LoginDto;
import com.urida.user.entity.User;

public interface UserService {
    User login(LoginDto loginDto);

    void saveLanguage(int lang);

    void checkNickname(String nickname);

    void saveNickname(String nickname);
}
