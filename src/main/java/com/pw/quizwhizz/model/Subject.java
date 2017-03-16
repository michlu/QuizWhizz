package com.pw.quizwhizz.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "subject")
public class Subject {
    @Id
    @Column(name = "id_subject")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "subject_name", nullable = false)
    private String subjectName;
    @Column(columnDefinition = "blob")
    private String description;

    @OneToMany(mappedBy = "questionsSubject", cascade = CascadeType.ALL)
    List<Question> question;

    public Subject() {
    }

    public Subject(String subjectName, String description) {
        this.subjectName = subjectName;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Question> getQuestion() {
        return question;
    }

    public void setQuestion(List<Question> question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", subjectName='" + subjectName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
