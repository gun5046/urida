package com.urida.user.service.impl;

import com.urida.user.dto.LoginDto;
import com.urida.user.entity.User;
import com.urida.user.repo.UserJpqlRepo;
import com.urida.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private  final UserJpqlRepo userJpqlRepo;

    @Override
    public void login(LoginDto loginDto) {
        User user = userJpqlRepo.findBySocialId(loginDto.getType(), loginDto.getId());

        if(user == null){
            System.out.println(1);
        }else{
            System.out.println(2);
        }
    }
}
