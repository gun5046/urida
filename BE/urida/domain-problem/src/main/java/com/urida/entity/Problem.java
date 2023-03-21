package com.urida.entity;

import com.urida.user.entity.User;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long pro_id;

    @Column(nullable = true)
    private int sentence_id;
    @Column(nullable = false)
    private int answer_id;

    @Column(nullable = false)
    private int type;
    @Column(nullable = false)
    private int category_id;
    @ColumnDefault("1")
    private int wrong_cnt;

    @ManyToOne
    @JoinColumn(name = "uid")
    private User user;
}
