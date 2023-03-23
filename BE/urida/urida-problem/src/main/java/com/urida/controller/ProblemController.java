package com.urida.controller;

import com.urida.dto.ProblemOutDto;
import com.urida.dto.ProblemSaveDto;
import com.urida.impl.ProblemService;
import com.urida.exception.InputException;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/problem")
@RequiredArgsConstructor
public class ProblemController {

    private final ProblemService problemService;
//    Logger logger = LoggerFactory.getLogger(ProblemController.class);

    @GetMapping("/info")
    @ApiOperation(value = "문제정보", notes = "문제 Id / 해당 문제 없음 -> 빈 Problem 객체 리턴/ Input 값오류->403 error")
    public ProblemOutDto getProblemInfo(@RequestParam Long proId){
        return problemService.problemInfo(proId);
    }

    @PostMapping("/save")
    @ApiOperation(value = "문제저장", notes = " 카테고리 Id, 빈칸 문장의 경우 빈칸 문장 Id, 문제 타입, 답 Id, 틀린 횟수, userid/ ")
    public Boolean saveProblem(@Validated @RequestBody ProblemSaveDto problemSaveDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new InputException("RequestData(RegisterDto))invalid");
        }

        problemService.saveProblem(problemSaveDto);
        return true;
    }

    @PutMapping("/update")
    @ApiOperation(value = "문제갱신", notes = "틀린횟수 +1")
    public void updateProblem(@RequestParam Long proId){
        problemService.updateProblem(proId);
    }

    @GetMapping("/list")
    @ApiOperation(value = "해당유저의 틀린 문제 리스트", notes = "UserId 필요")
    public List<ProblemOutDto> getListProblem(@RequestParam Long userId){
        return problemService.getListProblem(userId);
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "리스트에서 문제 삭제", notes = "ProId 필요 (문제 리스트에서 특정 문제 삭제)")
    public void deleteProblem(@RequestParam Long proId){
        problemService.deleteProblem(proId);
    }
}
