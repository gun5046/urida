package com.urida.user.controller;


import com.urida.user.dto.LoginDto;
import com.urida.user.entity.User;
import com.urida.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public User login(@ModelAttribute LoginDto loginDto){
        User user = userService.login(loginDto);
        return user;
    }

    @PostMapping("/language")
    public Boolean saveLanguage(@RequestParam int lang){
        userService.saveLanguage(lang);
    }

    @GetMapping("/nickname")
    public Boolean checkNickname(@RequestParam String nickname){
        userService.checkNickname(nickname);
    }

    @PostMapping("/nickname")
    public Boolean saveNickname(@RequestParam String nickname){
        userService.saveNickname(nickname);
    }
}
