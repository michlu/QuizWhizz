package com.pw.quizwhizz.repository.impl;

import com.pw.quizwhizz.model.dto.UserAllStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Repozytorium udostępnia szczegolowe dane dla wynikow gier danego uzytkownika. Udostepnia metode zwracajaca liste obiektow DTO z danymi dla wszystkich gier uzytkownika
 * @author Michał Nowiński
 */
@Repository
public class UserAllScoresRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserAllScoresRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String userScores =
            "SELECT game.id, game.start_time, category.category_name, " +
            " (SELECT COUNT(*) FROM score WHERE score.game_id = game.id), " +
            " score.points, " +
            " (SELECT u.first_name FROM user u, score s WHERE s.isHighest = 1 AND s.user_id = u.id AND s.game_id = game.id) " +
            "FROM game  " +
            "INNER JOIN category ON game.category_id = category.id " +
            "INNER JOIN score ON game.id = score.game_id " +
            "INNER JOIN user ON score.user_id = user.id " +
            "WHERE user.id = ?;";

    public List<UserAllStats> findAllScoreForUser(Long userId){
        return jdbcTemplate.query(userScores, new UserAllScoresRowMapper(), userId);
    }
    /**
     * Pomocnicza klasa implementujaca interface RowMapper. Mapuje wiersze tabeli sql na obiekt javy.
     * @see RowMapper
     * @see UserAllStats
     */
     class UserAllScoresRowMapper implements RowMapper<UserAllStats> {
        @Override
        public UserAllStats mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            UserAllStats userAllStats = new UserAllStats();
            userAllStats.setGameId(resultSet.getInt(1));
            userAllStats.setGameDate(resultSet.getDate(2));
            userAllStats.setGameCategory(resultSet.getString(3));
            userAllStats.setNumberOfPlayers(resultSet.getInt(4));
            userAllStats.setPoints(resultSet.getInt(5));
            userAllStats.setWinner(resultSet.getObject(6)==null ? " " : resultSet.getString(6));
            return userAllStats;
        }
    }
}


