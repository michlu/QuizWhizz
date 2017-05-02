package com.pw.quizwhizz.service.impl;

import com.pw.quizwhizz.model.category.Category;
import com.pw.quizwhizz.model.question.Question;
import com.pw.quizwhizz.model.answer.Answer;
import com.pw.quizwhizz.repository.AnswerRepository;
import com.pw.quizwhizz.repository.CategoryRepository;
import com.pw.quizwhizz.repository.QuestionRepository;
import com.pw.quizwhizz.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class QuestionServiceImpl implements QuestionService {

    private Random random = new Random();

    private final QuestionRepository questionRepository;
    private final CategoryRepository categoryRepository;
    private final AnswerRepository answerRepository;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository, CategoryRepository categoryRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.categoryRepository = categoryRepository;
        this.answerRepository = answerRepository;
    }

    @Override
    public List<Question> getQuestionsForNewGame(long categoryId) {
        Category category = categoryRepository.findById(categoryId);
        return getRandomQuestions(category, 10);
    }

    @Override
    public List<Question> getRandomQuestions(Category category, int number) {
        List<Question> questions = new ArrayList<>();

        List<Question> allQuestions = questionRepository.findAllByCategory(category);
        int size = allQuestions.size();
        Question q;

        for (int i = 0; i < number; i++) {
            q = allQuestions.get(random.nextInt(size));
            if (!questions.contains(q)) {
                questions.add(q);
            } else {
                i--;
            }
        }
        return questions;
    }

    public List<Question> findAllByCategory(Category category){
        return questionRepository.findAllByCategory(category);
    }

    @Override
    public Question findById(Long Id) {
        return questionRepository.findById(Id);
    }

    @Transactional
    @Override
    public void deleteById(Long Id) {
        questionRepository.deleteById(Id);
    }

    @Override
    public void addQuestion(String categoryId, String inputQuestion, String inputAnswer1, String inputAnswer2, String inputAnswer3, String inputAnswer4, String answerCorrect) {
        Question question = new Question();
        question.setQuestion(inputQuestion);

        Category category = categoryRepository.findOne(Long.parseLong(categoryId));
        question.setCategory(category);

        Answer answer1 = new Answer();
        answer1.setAnswer(inputAnswer1);
        if("correct_1".equals(answerCorrect))
            answer1.setCorrect(true);
        Answer answer2 = new Answer();
        answer2.setAnswer(inputAnswer2);
        if("correct_2".equals(answerCorrect))
            answer2.setCorrect(true);
        Answer answer3 = new Answer();
        answer3.setAnswer(inputAnswer3);
        if("correct_3".equals(answerCorrect))
            answer3.setCorrect(true);
        Answer answer4 = new Answer();
        answer4.setAnswer(inputAnswer4);
        if("correct_4".equals(answerCorrect))
            answer4.setCorrect(true);
        List<Answer> answers = new ArrayList<>();
        answers.add(answer1);
        answers.add(answer2);
        answers.add(answer3);
        answers.add(answer4);
        question.setAnswers(answers);
        questionRepository.save(question);
    }

    @Transactional
    @Modifying
    @Override
    public void updateQuestion(String inputId,
                               String inputQuestion,
                               String inputAnswer1,
                               String answerId1,
                               String inputAnswer2,
                               String answerId2,
                               String inputAnswer3,
                               String answerId3,
                               String inputAnswer4,
                               String answerId4,
                               String answerCorrect) {
        Question question = questionRepository.findById(Long.parseLong(inputId));
        question.setQuestion(inputQuestion);

        System.out.println(answerCorrect + " " + inputAnswer1 + " " + inputAnswer2 + " " + inputAnswer3 + " " + inputAnswer4);

        Answer answer1 = answerRepository.findOne(Long.parseLong(answerId1));
        answer1.setAnswer(inputAnswer1);
        answer1.setCorrect(false);
        if("correct_1".equals(answerCorrect))
            answer1.setCorrect(true);
        Answer answer2 = answerRepository.findOne(Long.parseLong(answerId2));
        answer2.setAnswer(inputAnswer2);
        answer2.setCorrect(false);
        if("correct_2".equals(answerCorrect))
            answer2.setCorrect(true);
        Answer answer3 = answerRepository.findOne(Long.parseLong(answerId3));
        answer3.setAnswer(inputAnswer3);
        answer3.setCorrect(false);
        if("correct_3".equals(answerCorrect))
            answer3.setCorrect(true);
        Answer answer4 = answerRepository.findOne(Long.parseLong(answerId4));
        answer4.setAnswer(inputAnswer4);
        answer4.setCorrect(false);
        if("correct_4".equals(answerCorrect))
            answer4.setCorrect(true);

        answerRepository.save(answer1);
        answerRepository.save(answer2);
        answerRepository.save(answer3);
        answerRepository.save(answer4);

        questionRepository.save(question);
    }
}
