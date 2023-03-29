package com.urida.board.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

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

    private MultipartFile image;

    private int category_id;

    private Long uid;
}
