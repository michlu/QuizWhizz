package com.pw.quizwhizz.model.question;

import com.pw.quizwhizz.model.question.Question;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

// QuestionInGameDTO zostanie stworzone i zapisane do bazy po uzyskaniu pytan do konkretnej gry w kontrolerze
// Sekwencja wyswietlania pytan zostanie ustalona w kontrolerze przy pobieraniu listy pytan
// Dzieki temu wszycy gracze beda dostawac pytania w tej samej kolejnosci

@Entity
@Data
@NoArgsConstructor
@Table(name = "question_in_game")
public class QuestionInGameDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "question_id")
    Question question;

    @Column(name = "game_id")
    private long gameId;

    int sequence;

    public QuestionInGameDTO(Question question, long gameId, int sequence) {
        this.question = question;
        this.gameId = gameId;
        this.sequence = sequence;
    }
}
