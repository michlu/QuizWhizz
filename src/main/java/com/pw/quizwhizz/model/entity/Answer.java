package com.pw.quizwhizz.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "answer")
public class Answer {
    @Id
    @Column(name = "id_answer")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String answer;

    @Column(columnDefinition = "TINYINT DEFAULT FALSE", nullable = false)
    private Boolean isCorrect = false;

//    @ManyToOne
//    @JoinColumn(name = "question")
//    private Question question;

}
