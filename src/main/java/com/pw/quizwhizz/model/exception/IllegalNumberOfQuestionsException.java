package com.pw.quizwhizz.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Klasa wyjatku rzucanego w przypadku, gdy liczba pytań pobrana przez serwis jest niewystarczajaca
 *
 * @author Karolina Prusaczyk
 * @see Throwable
 */
public class IllegalNumberOfQuestionsException extends Throwable {
}
