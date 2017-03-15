package com.pw.quizwhizz.dao;

/**
 * Created by michlu on 13.03.17.
 */
public class DataAccessLayerException extends RuntimeException {
    public DataAccessLayerException() {
    }

    public DataAccessLayerException(String message) {
        super(message);
    }

    public DataAccessLayerException(Throwable cause) {
        super(cause);
    }

    public DataAccessLayerException(String message, Throwable cause) {
        super(message, cause);
    }
}