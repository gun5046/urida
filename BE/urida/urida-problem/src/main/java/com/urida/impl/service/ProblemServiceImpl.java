package com.urida.impl.service;

import com.urida.dto.ProblemOutDto;
import com.urida.dto.ProblemSaveDto;
import com.urida.exception.SaveException;
import com.urida.impl.ProblemService;
import com.urida.entity.Problem;
import com.urida.repo.ProblemJpqlRepo;
import com.urida.user.entity.User;
import com.urida.user.repo.UserJpqlRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProblemServiceImpl implements ProblemService {

    private final ProblemJpqlRepo problemJpqlRepo ;
    private final UserJpqlRepo userJpqlRepo;
    @Override
    public ProblemOutDto problemInfo(Long proId) {
        if(problemJpqlRepo.findByProId(proId).isPresent()){
            return new ProblemOutDto(problemJpqlRepo.findByProId(proId).get());
        }else{
            return new ProblemOutDto();
        }
    }

    @Override
    @Transactional
    public void saveProblem(ProblemSaveDto problemSaveDto) {
        Optional<User> user = userJpqlRepo.findByUid(problemSaveDto.getUid());

        if (user.isPresent()){
            Problem problem = Problem.builder()
                    .sentence_id(problemSaveDto.getSentence_id())
                    .answer_id(problemSaveDto.getAnswer_id())
                    .type(problemSaveDto.getType())
                    .category_id(problemSaveDto.getCategory_id())
                    .wrong_cnt(problemSaveDto.getWrong_cnt())
                    .choices(new ArrayList<>())
                    .examples(new ArrayList<>())
                    .user(user.get())
                    .build();

            problemSaveDto.getChoices().forEach(
                    choice -> problem.addChoice(choice)
            );
            problemSaveDto.getExamples().forEach(
                    example -> problem.addExample(example)
            );

            try {
                problemJpqlRepo.saveProblem(problem);
            }catch(Exception e){
                throw new SaveException("Value invalid");
            }
        }
    }

    @Override
    @Transactional
    public void updateProblem(Long proId) {
        problemJpqlRepo.updateProblem(proId);
    }

    @Override
    public List<ProblemOutDto> getListProblem(Long userId) {
        List<Problem> problems = problemJpqlRepo.findByUserId(userId);
        return problems.stream().map(problem -> new ProblemOutDto(problem)
        ).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteProblem(Long proId) {
        problemJpqlRepo.deleteProInList(proId);
    }
}
