package ru.nsu.fit.tsukanov.calculator.core.Exceptions;

public class CalculatorException extends Exception{
    public CalculatorException(){
        super("Some calculator exception");
    }

    public CalculatorException(String message) {
        super(message);
    }
}
