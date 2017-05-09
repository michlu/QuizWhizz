package com.pw.quizwhizz.entity;

import com.pw.quizwhizz.entity.game.CategoryEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "game_stats")
public class GameStatsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "game_id")
    private Long gameId;

    @OneToOne
    @JoinColumn(name = "category_id")
    CategoryEntity category;


//    List<User> users;

    @Column(name = "date_played")
    @Temporal(TemporalType.DATE)
    private Date gameDate = new Date();
}
