package com.pw.quizwhizz.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "question")
public class Question {
    @Id
    @Column(name = "id_question")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(columnDefinition = "blob", nullable = false)
    private String question;

    @ManyToOne
    @JoinColumn(name = "subject")
    private Subject questionsSubject;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "question")
    List<Answer> answer;

    public Question() {
    }

    public void addAnswer(Answer addAnswer){
        if(null == this.answer)
            this.answer = new ArrayList<>();
        answer.add(addAnswer);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<Answer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Answer> answer) {
        this.answer = answer;
    }

    public Subject getSubject() {
        return questionsSubject;
    }

    public void setSubject(Subject questionsSubject) {
        this.questionsSubject = questionsSubject;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", questionsSubject=" + questionsSubject +
                ", answer=" + answer +
                '}';
    }
}
