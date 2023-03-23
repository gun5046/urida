package com.urida.dto;

import com.urida.entity.Choice;
import com.urida.entity.Example;
import com.urida.entity.Problem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProblemOutDto {

    private Long pro_id;
    private int sentence_id ;

    private int answer_id;

    @Max(4)
    private int type;

    @Max(6)
    private int category_id;

    private int wrong_cnt;

    private List<Integer> choices = new ArrayList<>();

    private List<Integer> examples = new ArrayList<>();


    public ProblemOutDto(Problem problem){
        this.pro_id = problem.getPro_id();
        this.answer_id = problem.getAnswer_id();
        this.type = problem.getType();
        this.wrong_cnt = problem.getWrong_cnt();
        this.sentence_id = problem.getSentence_id();
        this.category_id = problem.getCategory_id();

        this.examples = problem.getExamples().stream().map(example->{
            return example.getWordId();
        }).collect(Collectors.toList());
        this.choices = problem.getChoices().stream().map(example->{
            return example.getWordId();
        }).collect(Collectors.toList());
    }
}