package com.urida.board.dto.response;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class BoardListDto {
    private Long board_id;

    private String title;

    private String content;

    private int view;

    private String dateTime;

    private int likeCnt;

    private int commentCnt;

    private String nickname;
}
