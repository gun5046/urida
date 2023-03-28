package com.urida.board.entity;

import com.urida.comment.entity.Comment;
import com.urida.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long board_id;

    private String title;

    private String content;

    @ColumnDefault("0")
    private int view;

    private String time;

//    @ColumnDefault("0")
//    private int assessment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid")
    private User user;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Comment> comment = new ArrayList<>();

    // == 비지니스 로직 == //
    
    /* 조회 수 증가*/
    public void addView(){
        this.view++;
    }

//    /* 좋아요 추가*/
//    public void like(){
//        this.assessment++;
//    }
//
//    /* 좋아요 취소*/
//    public void dislike(){
//        if(this.assessment > 0) {
//            this.assessment--;
//        }
//    }

}
