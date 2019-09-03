package com.korpodrony.exceptions;

public class NoElementFoundException extends Exception{
    private static  String MESSAGE = "No such element found";

    public NoElementFoundException() {
        super(MESSAGE);
    }
}
