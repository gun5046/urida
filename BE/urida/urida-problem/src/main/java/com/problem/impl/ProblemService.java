package com.problem.impl;

import com.problem.dto.ProblemSaveDto;
import com.urida.entity.Problem;

import java.util.List;

public interface ProblemService {
    Problem problemInfo(Long proId);

    void saveProblem(ProblemSaveDto problemSaveDto);

    List<Problem> getListProblem(Long userId);
}
