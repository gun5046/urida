package com.problem.impl.service;

import com.problem.dto.ProblemSaveDto;
import com.problem.impl.ProblemService;
import com.urida.entity.Problem;
import com.urida.repo.ProblemJpqlRepo;
import com.urida.user.entity.User;
import com.urida.user.repo.UserJpqlRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProblemServiceImpl implements ProblemService {

    private final ProblemJpqlRepo problemJpqlRepo ;
    private final UserJpqlRepo userJpqlRepo;
    @Override
    public Problem problemInfo(Long proId) {
        Optional<Problem> problem = problemJpqlRepo.findByProId(proId);
        if(problem.isPresent()){
            return problem.get();
        }else{
            return new Problem();
        }
    }

    @Override
    @Transactional
    public void saveProblem(ProblemSaveDto problemSaveDto) {
        Optional<User> user = userJpqlRepo.findByUid(problemSaveDto.getUid());

        if (user.isPresent()){
            Problem problem = Problem.builder()
                    .sentenceId(problemSaveDto.getSentence_id())
                    .answerId(problemSaveDto.getAnswer_id())
                    .type(problemSaveDto.getType())
                    .categoryId(problemSaveDto.getCategory_id())
                    .wrongCnt(problemSaveDto.getWrong_cnt())
                    .user(user.get())
                    .build();
            try {
                problemJpqlRepo.saveProblem(problem);
            }catch(Exception e){
            }
        }
    }

    @Override
    public List<Problem> getListProblem(Long userId) {
        List<Problem> problems = problemJpqlRepo.findByUserId(userId);

        return problems;
    }
}
