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
        userService.saveUser(registerDto);
        return true;
    }

    @GetMapping("/nickname")
    public Boolean checkNickname(@RequestParam String nickname){
        return userService.checkNickname(nickname);
    }

    @GetMapping("/info")
    public User getUserInfo(@RequestParam Long uid){
        return userService.getUserInfo(uid);
    }
}
