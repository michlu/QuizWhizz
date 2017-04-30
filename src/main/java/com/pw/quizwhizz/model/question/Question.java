package com.pw.quizwhizz.model.question;

import com.pw.quizwhizz.model.answer.Answer;
import com.pw.quizwhizz.model.category.Category;
import lombok.*;
import javax.persistence.*;
import java.util.List;

/**
 * Created by Karolina on 24.03.2017.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category")
    private Category category;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String question;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "question")
    private List<Answer> answers;

    public Question(String question, Category category, List<Answer> answers) {
        this.category = category;
        this.question = question;
        this.answers = answers;
    }
}

