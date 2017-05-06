package com.pw.quizwhizz.dto.game;

import com.pw.quizwhizz.model.game.Answer;
import com.pw.quizwhizz.model.game.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * Created by karol on 03.05.2017.
 */
@Data
@Entity
@Table(name = "question")
public class QuestionDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category")
    private CategoryDTO category;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String question;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "question")
    private List<AnswerDTO> answers;

}