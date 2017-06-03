package com.pw.quizwhizz.entity.game;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * Encja Question. Posiada relacje jednostronna jeden do jednego z Kategorią(Category) oraz relacje jednostronna jeden do wielu z Odpowiedzi(Answer).
 * Wyszukiwać pytania po kategorii, odpowiedzi po pytaniach.
 * @author Michał Nowiński
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