package com.urida.likeboard.entity;

import com.urida.board.entity.Board;
import com.urida.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeBoard {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long like_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;
}
