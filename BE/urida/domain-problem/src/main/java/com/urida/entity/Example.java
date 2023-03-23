package com.urida.entity;

import com.urida.user.entity.User;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Setter
public class Example {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private int wordId;

    @ManyToOne
    @JoinColumn(name = "pro_id")
    private Problem problem;

    public Example(int wordId, Problem problem){
        this.problem = problem;
        this.wordId = wordId;
    }
}
