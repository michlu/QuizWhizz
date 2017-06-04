package com.pw.quizwhizz.model.exception;

/**
 * Klasa wyjatku rzucanego w przypadku, gdy gracz probuje uzyskac ostateczne wyniki rozgrywki
 * zanim przejdzie ona w stan zakonczony.
 *
 * @author Karolina Prusaczyk
 * @see Throwable
 */
public class ScoreCannotBeRetrievedBeforeGameIsClosedException extends Throwable {
}
