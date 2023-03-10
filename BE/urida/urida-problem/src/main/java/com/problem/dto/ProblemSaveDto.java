package com.problem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProblemSaveDto {

    private int sentence_id ;

    @NotBlank
    private int answer_id;

    @NotEmpty
    @Max(4)
    private int type;

    @NotBlank
    @Max(6)
    private int category_id;

    @NotBlank
    private int wrong_cnt;

    @NotEmpty
    private Long uid;
}
