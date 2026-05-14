package io.github.guilhermegodoydev.greenhorizon.core.exceptions;

public class InsufficientFundsException extends Exception {
    private final int errorCode;

    public InsufficientFundsException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

}
