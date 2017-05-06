package com.pw.quizwhizz.dto.game;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

// QuestionInGameDTO zostanie stworzone i zapisane do bazy po uzyskaniu pytan do konkretnej gry w kontrolerze
// Sekwencja wyswietlania pytan zostanie ustalona w kontrolerze przy pobieraniu listy pytan
// Dzieki temu wszycy gracze beda dostawac pytania w tej samej kolejnosci

@Entity
@Getter @Setter
@Table(name = "question_in_game")
public class QuestionInGameDTO {
    @EmbeddedId
    private QuestionInGameKey id;

    @Column
    private int sequence;
}
