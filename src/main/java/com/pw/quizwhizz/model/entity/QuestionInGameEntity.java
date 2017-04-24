package com.pw.quizwhizz.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// QuestionInGameEntity zostanie stworzone i zapisane do bazy po uzyskaniu pytan do konkretnej gry w kontrolerze
// Sekwencja wyswietlania pytan zostanie ustalona w kontrolerze przy pobieraniu listy pytan
// Dzieki temu wszycy gracze beda dostawac pytania w tej samej kolejnosci

@Entity
@Data
@NoArgsConstructor
@Table(name = "question_in_game")
public class QuestionInGameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "question_id")
    Question question;

    @Column(name = "game_id")
    private long gameId;

    int sequence;
}
