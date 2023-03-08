package com.urida.user.service.impl;

import com.urida.user.dto.LoginDto;
import com.urida.user.dto.RegisterDto;
import com.urida.user.entity.User;
import com.urida.user.repo.UserJpqlRepo;
import com.urida.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Override
    public void saveUser(RegisterDto registerDto) {
        User user = User.builder()
                .nickname(registerDto.getNickname())
                .social_id(registerDto.getSocial_id())
                .type(registerDto.getType())
                .language(registerDto.getLanguage())
                .build();

        userJpqlRepo.saveUser(user);
    }

    @Override
    public Boolean checkNickname(String nickname) {

        Optional<User> user = userJpqlRepo.checkNickname(nickname);

        if(user.isPresent()){
            return false;
        }else{
            return true;
        }
    }
}
