package com.vigneshvrt.coffeemachine.core.exception;

public class CannotPrepareBeverageException extends Exception {

    public CannotPrepareBeverageException(String message) {
        super(message);
    }

    public CannotPrepareBeverageException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
