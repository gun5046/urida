package com.urida.board.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ArticleRequestDto {
    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    private int category_id;

    private Long uid;
}
