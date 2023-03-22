package com.urida.entity;

import com.urida.user.entity.User;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL)
    private List<Choice> choices;

    public void addChoice(int wordId){
        choices.add(new Choice(wordId, this));
    }

    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL)
    private List<Example> examples;

    public void addExample(int wordId){
        examples.add(new Example(wordId, this));
    }
}
