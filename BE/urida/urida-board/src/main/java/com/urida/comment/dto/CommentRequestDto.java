package com.urida.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRequestDto {

    @NotEmpty
    private String content;

    private String dateTime;

    private Long board_id;

    private Long uid;
}
