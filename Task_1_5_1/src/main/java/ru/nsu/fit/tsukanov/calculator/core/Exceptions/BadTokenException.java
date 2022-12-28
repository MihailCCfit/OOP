package ru.nsu.fit.tsukanov.calculator.core.Exceptions;

public class BadTokenException extends CalculatorException {
    public BadTokenException() {
        super("Bad lexeme");
    }

    public BadTokenException(String message) {
        super(message);
    }
}
