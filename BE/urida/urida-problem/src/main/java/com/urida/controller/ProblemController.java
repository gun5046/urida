package com.urida.controller;

import com.urida.dto.ProblemSaveDto;
import com.urida.impl.ProblemService;
import com.urida.entity.Problem;
import com.urida.exception.InputException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/problem")
@RequiredArgsConstructor
public class ProblemController {

    private final ProblemService problemService;
    Logger logger = LoggerFactory.getLogger(ProblemController.class);

    @GetMapping("/info")
    public Problem getProblemInfo(@RequestParam Long proId){
        return problemService.problemInfo(proId);
    }

    @PostMapping("/save")
    public Boolean saveProblem(@Validated @RequestBody ProblemSaveDto problemSaveDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new InputException("RequestData(RegisterDto))invalid");
        }

        System.out.println(problemSaveDto);
        problemService.saveProblem(problemSaveDto);
        return true;
    }

    @GetMapping("/list")
    public List<Problem> getListProblem(@RequestParam Long userId){
        return problemService.getListProblem(userId);
    }

    @DeleteMapping("/delete")
    public void deleteProblem(@RequestParam Long userId, @RequestParam Long proId){
        problemService.deleteProblem(userId, proId);
    }
}
