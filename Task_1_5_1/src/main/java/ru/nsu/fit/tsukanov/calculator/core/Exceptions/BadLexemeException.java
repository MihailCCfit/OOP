package ru.nsu.fit.tsukanov.calculator.core.Exceptions;

public class BadLexemeException extends CalculatorException{
    public BadLexemeException(){
        super("Bad lexeme");
    }

    public BadLexemeException(String message) {
        super(message);
    }
    public BadLexemeException(String message, Throwable cause) {
        super(message, cause);
    }
}
