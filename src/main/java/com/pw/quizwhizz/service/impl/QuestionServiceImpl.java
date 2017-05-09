package com.pw.quizwhizz.service.impl;

import com.pw.quizwhizz.dto.game.AnswerDTO;
import com.pw.quizwhizz.dto.game.QuestionDTO;
import com.pw.quizwhizz.model.exception.IllegalNumberOfQuestionsException;
import com.pw.quizwhizz.model.game.Category;
import com.pw.quizwhizz.model.game.Question;
import com.pw.quizwhizz.model.game.Answer;
import com.pw.quizwhizz.repository.game.AnswerRepository;
import com.pw.quizwhizz.repository.game.CategoryRepository;
import com.pw.quizwhizz.repository.game.QuestionRepository;
import com.pw.quizwhizz.service.AnswerService;
import com.pw.quizwhizz.service.CategoryService;
import com.pw.quizwhizz.service.QuestionService;
import com.pw.quizwhizz.service.exception.NoQuestionsInDBException;
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
    private final CategoryService categoryService;
    private final AnswerService answerService;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository, AnswerRepository answerRepository, AnswerService answerService, CategoryService categoryService, CategoryRepository categoryRepository) {
        this.questionRepository = questionRepository;
        this.answerService = answerService;
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    @Override
    public List<Question> getQuestionsForNewGame(long categoryId) throws NoQuestionsInDBException {
        return getRandomQuestionsByCategoryId(categoryId, 10);
    }

    @Override
    public List<Question> getRandomQuestionsByCategoryId(long categoryId, int number) throws NoQuestionsInDBException {
        List<Question> questions = new ArrayList<>();

        List<QuestionDTO> allQuestions = questionRepository.findAllByCategory_Id(categoryId);
        int size = allQuestions.size();

        if (size == 0){
            throw new NoQuestionsInDBException();
        }

        for (int i = 0; i < number; i++) {
            QuestionDTO questionDTO = allQuestions.get(random.nextInt(size));
            Question question = convertToQuestion(questionDTO);

            if (!questions.contains(question)) {
                questions.add(question);
            } else {
                i--;
            }
        }
        return questions;
    }

    @Override
    public List<Question> getRandomQuestionsByCategory(Category category, int number) throws NoQuestionsInDBException {
        long id = category.getId();
        return getRandomQuestionsByCategoryId(id, number);
    }

    @Override
    public List<Question> findAllByCategoryId(long categoryId){
        List<Question> questions = new ArrayList<>();
        List<QuestionDTO> questionsDTO = questionRepository.findAllByCategory_Id(categoryId);

        for (QuestionDTO questionDTO : questionsDTO) {
            Question question = new Question();
            question.setId(questionDTO.getId());
            question.setCategory(categoryService.findById(categoryId));
            question.setQuestion(questionDTO.getQuestion());
            question.setAnswers(answerService.getAllByQuestionId(questionDTO.getId()));
            questions.add(question);
        }
        return questions;
    }

    @Override
    public List<Question> findAllByCategory(Category category){
        return findAllByCategoryId(category.getId());
    }

    @Transactional
    @Override
    public Question findById(Long id) {
       QuestionDTO questionDTO = questionRepository.getOne(id);
        Question question = new Question();
        question.setId(id);
        question.setCategory(categoryService.findById(questionDTO.getCategory().getId()));
        question.setQuestion(questionDTO.getQuestion());
        question.setAnswers(answerService.getAllByQuestionId(questionDTO.getId()));
        return question;
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        questionRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void addQuestion(String categoryId, String inputQuestion, String inputAnswer1, String inputAnswer2, String inputAnswer3, String inputAnswer4, String answerCorrect) {
        long categoryIdL = Long.parseLong(categoryId);
        Category category = categoryService.findById(categoryIdL);
        Question question = new Question();
        question.setQuestion(inputQuestion);
        question.setCategory(category);
        List<Answer> answers = addAnswers(inputAnswer1, inputAnswer2, inputAnswer3, inputAnswer4, answerCorrect);
        question.setAnswers(answers);

        QuestionDTO questionDTO = new QuestionDTO();
        List<AnswerDTO> answersDTO = answerService.saveAsDTO(answers);
        assignValuesFromQuestion(question, questionDTO);
        questionDTO.setAnswers(answersDTO);
        questionRepository.save(questionDTO);
        question.setId(questionDTO.getId());
    }

    //TODO: Test if correct (esp. doubled values in DB)
    @Transactional
    @Modifying
    @Override
    public void updateQuestion(String inputId, String inputQuestion, String inputAnswer1, String inputAnswer2, String inputAnswer3, String inputAnswer4, String answerCorrect) {
        System.out.println(answerCorrect + " " + inputAnswer1 + " " + inputAnswer2 + " " + inputAnswer3 + " " + inputAnswer4);

        long questionId = Long.parseLong(inputId);
        Question question = findById(questionId);
        question.setQuestion(inputQuestion);
        updateAnswersInQuestion(inputAnswer1, inputAnswer2, inputAnswer3, inputAnswer4, answerCorrect, questionId);

        QuestionDTO questionDTO = questionRepository.findOne(questionId);
        assignValuesFromQuestion(question, questionDTO);
        questionRepository.saveAndFlush(questionDTO);
    }

    private void assignValuesFromQuestion(Question question, QuestionDTO questionDTO) {
        questionDTO.setCategory(categoryRepository.findOne(question.getCategory().getId()));
        questionDTO.setQuestion(question.getQuestion());
    }

    private Question convertToQuestion(QuestionDTO questionDTO) {
        Question question = new Question();
        question.setId(questionDTO.getId());
        question.setCategory(categoryService.findById(questionDTO.getCategory().getId()));
        question.setQuestion(questionDTO.getQuestion());
        question.setAnswers(answerService.getAllByQuestionId(questionDTO.getId()));
        return question;
    }

    private List<Answer> addAnswers(String inputAnswer1, String inputAnswer2, String inputAnswer3, String inputAnswer4, String answerCorrect) {
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
        return answers;
    }

    private void updateAnswersInQuestion(String inputAnswer1, String inputAnswer2, String inputAnswer3, String inputAnswer4, String answerCorrect, long questionId) {
        List<Answer> answers = answerService.getAllByQuestionId(questionId);
        for (Answer answer : answers) {
            answer.setCorrect(false);
        }
        Answer answer1 = answers.get(0);
        answer1.setAnswer(inputAnswer1);
        if ("correct_1".equals(answerCorrect))
            answer1.setCorrect(true);
        Answer answer2 = answers.get(1);
        answer2.setAnswer(inputAnswer2);
        if ("correct_2".equals(answerCorrect))
            answer2.setCorrect(true);
        Answer answer3 = answers.get(2);
        answer3.setAnswer(inputAnswer3);
        if ("correct_3".equals(answerCorrect))
            answer3.setCorrect(true);
        Answer answer4 = answers.get(3);
        answer4.setAnswer(inputAnswer4);
        if ("correct_4".equals(answerCorrect))
            answer4.setCorrect(true);

        answerService.updateAsDTO(answers);
    }
}
