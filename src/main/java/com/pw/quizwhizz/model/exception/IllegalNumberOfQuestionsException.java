package com.pw.quizwhizz.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Karolina on 14.04.2017.
 */
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class IllegalNumberOfQuestionsException extends Throwable {
}
