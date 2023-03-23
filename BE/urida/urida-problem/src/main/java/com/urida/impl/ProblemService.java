package com.urida.impl;

import com.urida.dto.ProblemSaveDto;
import com.urida.entity.Problem;

import java.util.List;

public interface ProblemService {
    Problem problemInfo(Long proId);

    void saveProblem(ProblemSaveDto problemSaveDto);

    void updateProblem(Long proId);

    List<Problem> getListProblem(Long userId);

    void deleteProblem(Long userId, Long proId);
}
