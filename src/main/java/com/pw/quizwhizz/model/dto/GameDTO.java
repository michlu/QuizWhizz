package com.pw.quizwhizz.model.dto;

import lombok.Getter;
import lombok.Setter;
/**
 * Klasa DTO dostarczajaca postawowych informacji dotyczacych aktualnych gier,
 * do wyswietlenia na glownej stronie portalu.
 *
 * @author Karolina Prusaczyk
 */
@Getter @Setter
public class GameDTO {
    private long gameId;
    private String categoryName;
    private String ownerName;
    private String displayStateName;
}
