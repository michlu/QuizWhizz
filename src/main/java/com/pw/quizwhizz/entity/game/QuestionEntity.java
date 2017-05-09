package com.pw.quizwhizz.entity.game;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * Created by karol on 03.05.2017.
 */
@Data
@Entity
@Table(name = "question")
public class QuestionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category")
    private CategoryEntity category;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String question;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "question")
    private List<AnswerEntity> answers;

}