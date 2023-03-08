package com.urida.user.service.impl;

import com.urida.user.dto.LoginDto;
import com.urida.user.entity.User;
import com.urida.user.repo.UserJpqlRepo;
import com.urida.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private  final UserJpqlRepo userJpqlRepo;

    @Override
    public User login(LoginDto loginDto) {
        Optional<User> user = userJpqlRepo.findBySocialId(loginDto.getType(), loginDto.getId());

        if(user.isPresent()){
            return user.get();
        }else{
            return new User();
        }
    }

    @Override
    public void saveLanguage(int lang) {

    }

    @Override
    public void checkNickname(String nickname) {

    }

    @Override
    public void saveNickname(String nickname) {

    }
}
