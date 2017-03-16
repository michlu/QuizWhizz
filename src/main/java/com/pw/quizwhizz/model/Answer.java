package com.pw.quizwhizz.model;

import javax.persistence.*;

@Entity
@Table(name = "answer")
public class Answer {
    @Id
    @Column(name = "id_answer")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(columnDefinition = "BLOB", nullable = false)
    private String answer;
    //TODO adnotacja nie ustawia defaultowego ustawienia
    @Column(columnDefinition = "TINYINT DEFAULT FALSE", nullable = false)
    private Boolean correct = false;

    @ManyToOne
    @JoinColumn(name = "question")
    private Question question;

    public Answer() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Boolean getCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", answer='" + answer + '\'' +
                ", correct=" + correct +
                '}';
    }
}
