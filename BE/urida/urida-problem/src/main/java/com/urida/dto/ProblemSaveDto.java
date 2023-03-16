package com.urida.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProblemSaveDto {

    private int sentence_id ;

    private int answer_id;

    @Max(4)
    private int type;

    @Max(6)
    private int category_id;

    private int wrong_cnt;

    private Long uid;
}
