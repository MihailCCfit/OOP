package ru.nsu.fit.tsukanov.calculator.core.Exceptions;

public class NotNumberException extends CalculatorException {
    public NotNumberException() {
        super("Not a number");
    }

    public NotNumberException(String message) {
        super(message);
    }

    public NotNumberException(String message, Throwable cause) {
        super(message, cause);
    }
}
