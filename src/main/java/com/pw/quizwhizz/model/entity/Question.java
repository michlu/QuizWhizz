package com.pw.quizwhizz.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "question")
public class Question {
    @Id
    @Column(name = "id_question")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String question;

    @ManyToOne
    @JoinColumn(name = "category")
    private Category category;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "question")
    private List<Answer> answers = new ArrayList<>();

    public void addAnswer(Answer... addAnswers){
        for (Answer addAnswer : addAnswers) {
            answers.add(addAnswer);
        }
    }

}
