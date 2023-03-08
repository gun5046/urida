package com.urida.user.controller;


import com.urida.user.dto.LoginDto;
import com.urida.user.dto.RegisterDto;
import com.urida.user.entity.User;
import com.urida.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/login")
    public User login(@ModelAttribute LoginDto loginDto){
        User user = userService.login(loginDto);
        return user;
    }

    @PostMapping("/register")
    public Boolean saveUser(@RequestBody RegisterDto registerDto){
        try {
            System.out.println(registerDto.getSocial_id());
            userService.saveUser(registerDto);
        }catch(Exception e){
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }

    @GetMapping("/nickname")
    public Boolean checkNickname(@RequestParam String nickname){
        return userService.checkNickname(nickname);
    }
}
