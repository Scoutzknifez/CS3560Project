package com.CS3560Project.exceptions;


public class ParseFailureException extends RuntimeException {
    public ParseFailureException(Object failedOn, Class parseTo) {
        super("Failed to parse " + failedOn + " (" + failedOn.getClass().getSimpleName() + ") into type " + parseTo.getSimpleName());
    }
}