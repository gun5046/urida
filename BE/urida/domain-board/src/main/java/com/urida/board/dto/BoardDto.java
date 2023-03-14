package com.urida.board.dto;

import com.urida.user.entity.User;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Builder
public class BoardDto {
    private Long board_id;

    private String title;

    private String content;

    private int view;

    private String dateTime;

    private int assessment;

    private Long uid;
}
