package com.urida.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long proId;

    @Column(nullable = true)
    private int sentenceId;
    @Column(nullable = false)
    private int answerId;

    @Column(nullable = false)
    private int type;
    @Column(nullable = false)
    private int categoryId;
    @ColumnDefault("1")
    private int wrongCnt;

    @ManyToOne
    @JoinColumn(name = "user_uid")
    private User user;



}
