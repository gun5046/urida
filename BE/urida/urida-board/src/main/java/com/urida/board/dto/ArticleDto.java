package com.urida.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleDto {

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    private LocalDateTime dateTime;

    private String writer;
}
