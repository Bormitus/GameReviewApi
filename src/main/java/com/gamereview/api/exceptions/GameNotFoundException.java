package com.gamereview.api.exceptions;

public class GameNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1;

    public GameNotFoundException(String message) {
        super(message);
    }
}
