package com.pw.quizwhizz.dto.game;

import com.pw.quizwhizz.model.account.User;
import com.pw.quizwhizz.model.game.Player;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "player_in_game")
public class PlayerInGameDTO {
    @EmbeddedId
    private PlayerInGameKey id;

    @Column(name = "owner")
    private boolean isOwner;
}
