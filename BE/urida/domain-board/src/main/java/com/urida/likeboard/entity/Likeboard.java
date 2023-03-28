package com.urida.likeboard.entity;

import com.urida.board.entity.Board;
import com.urida.user.entity.User;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Likeboard {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long like_id;

    private boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;
}
