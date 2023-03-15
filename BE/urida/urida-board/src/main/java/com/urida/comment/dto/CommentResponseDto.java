package com.urida.comment.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class CommentResponseDto {

    @NotEmpty
    private String content;

    private String dateTime;

    @NotEmpty
    private Long board_id;

    private Long uid;
}
