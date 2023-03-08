package com.urida.user.controller;


import com.urida.user.dto.LoginDto;
import com.urida.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public boolean login(@ModelAttribute LoginDto loginDto){
        userService.login(loginDto);
        return true;
    }
}
