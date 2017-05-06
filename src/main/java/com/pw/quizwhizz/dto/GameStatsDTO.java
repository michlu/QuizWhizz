package com.pw.quizwhizz.dto;

import com.pw.quizwhizz.dto.game.CategoryDTO;
import com.pw.quizwhizz.model.game.Category;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "game_stats")
public class GameStatsDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "game_id")
    private Long gameId;

    @OneToOne
    @JoinColumn(name = "category_id")
    CategoryDTO category;


//    List<User> users;

    @Column(name = "date_played")
    @Temporal(TemporalType.DATE)
    private Date gameDate = new Date();
}
