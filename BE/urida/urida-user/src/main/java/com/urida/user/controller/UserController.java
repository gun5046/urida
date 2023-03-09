package com.urida.user.controller;


import com.urida.exception.InputException;
import com.urida.user.dto.LoginDto;
import com.urida.user.dto.RegisterDto;
import com.urida.user.entity.User;
import com.urida.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/login")
    public User login(@Validated @ModelAttribute LoginDto loginDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new InputException("RequestData(LoginDto) invalid");
        }
        User user = userService.login(loginDto);
        return user;
    }

    @PostMapping("/register")
    public Boolean saveUser(@Validated @RequestBody RegisterDto registerDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new InputException("RequestData(RegisterDto))invalid");
        }
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
