package com.pw.quizwhizz.repository.game;

import com.pw.quizwhizz.model.game.Category;
import com.pw.quizwhizz.model.game.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByCategory(Category category);
    Question findById(Long id);
    void deleteById(Long id);
}
