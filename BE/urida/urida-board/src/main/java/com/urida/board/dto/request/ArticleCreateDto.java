package com.urida.board.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleCreateDto {

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    private String dateTime;

    private Long uid;
}
