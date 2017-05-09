package com.pw.quizwhizz.entity.game;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "player_in_game")
public class PlayerInGameEntity {
    @EmbeddedId
    private PlayerInGameKey id;

    @Column(name = "owner")
    private boolean isOwner;
}
