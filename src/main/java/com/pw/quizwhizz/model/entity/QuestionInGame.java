package com.pw.quizwhizz.model.entity;

import com.pw.quizwhizz.model.Game;
import lombok.Data;
import lombok.NoArgsConstructor;

// QuestionInGame zostanie stworzone i zapisane do bazy po uzyskaniu pytan do konkretnej gry w kontrolerze
// Sekwencja wyswietlania pytan zostanie ustalona w kontrolerze przy pobieraniu listy pytan
// Dzieki temu wszycy gracze beda dostawac pytania w tej samej kolejnosci

@Data
@NoArgsConstructor
public class QuestionInGame {
// Game_id i question_id:
    Question question;
    Game game;
    int sequence;

    public QuestionInGame(Question question, Game game, int sequence) {
        this.question = question;
        this.game = game;
        this.sequence = sequence;
    }
}
git add .