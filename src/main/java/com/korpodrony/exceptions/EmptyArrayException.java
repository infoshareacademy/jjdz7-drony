package com.korpodrony.exceptions;

public class EmptyArrayException extends Exception {
    private static final String MESSAGE = "There is nothing to remove";

    public EmptyArrayException() {
        super(MESSAGE);
    }
}
