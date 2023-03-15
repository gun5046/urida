package com.urida.comment.dto;

import lombok.*;
import org.springframework.beans.factory.annotation.Required;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CommentRequestDto {

    @NotEmpty
    private String content;

    private String dateTime;

    @NotEmpty
    private Long board_id;

    private Long uid;
}
