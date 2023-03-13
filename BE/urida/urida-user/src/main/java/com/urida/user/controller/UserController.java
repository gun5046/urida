package com.urida.user.controller;


import com.urida.exception.InputException;
import com.urida.user.dto.LoginDto;
import com.urida.user.dto.RegisterDto;
import com.urida.user.entity.User;
import com.urida.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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


    @ApiOperation(value = "로그인", notes = "카카오, 네이버의 소셜id값 / User 없음 -> 빈 User객체 리턴/ Input 값오류->403 error")
    @GetMapping("/login")
    public User login(@Validated @ModelAttribute LoginDto loginDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new InputException("RequestData(LoginDto) invalid");
        }
        User user = userService.login(loginDto);
        return user;
    }

    @PostMapping("/register")
    @ApiOperation(value = "유저가입", notes = "RegisterDto 받아서 유저 DB저장/ Boolean값 리턴, Input 값 오류 -> 403 error ")
    public Boolean saveUser(@Validated @RequestBody RegisterDto registerDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new InputException("RequestData(RegisterDto))invalid");
        }
        userService.saveUser(registerDto);
        return true;
    }

    @GetMapping("/nickname")
    @ApiOperation(value = "닉네임 중복 확인", notes = "닉네임 없음 -> true 리턴 / 있음 -> false")
    public Boolean checkNickname(@RequestParam String nickname){
        return userService.checkNickname(nickname);
    }

    @GetMapping("/info")
    @ApiOperation(value = "유저 정보 받아오기", notes = "Uid 주면 User 객체 리턴 / 해당하는 uid 없으면 에러 발생")
    public User getUserInfo(@RequestParam Long uid){
        return userService.getUserInfo(uid);
    }
}
