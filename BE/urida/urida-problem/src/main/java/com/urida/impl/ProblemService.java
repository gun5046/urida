package com.urida.impl;

import com.urida.dto.ProblemOutDto;
import com.urida.dto.ProblemSaveDto;

import java.util.List;

public interface ProblemService {
    ProblemOutDto problemInfo(Long proId);

    void saveProblem(ProblemSaveDto problemSaveDto);

    void updateProblem(Long proId);

    List<ProblemOutDto> getListProblem(Long userId);

    void deleteProblem(Long proId);
}
