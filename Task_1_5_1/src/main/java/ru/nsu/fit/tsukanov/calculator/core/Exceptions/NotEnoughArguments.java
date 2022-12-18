package ru.nsu.fit.tsukanov.calculator.core.Exceptions;

public class NotEnoughArguments extends CalculatorException {
    public NotEnoughArguments() {
        super("not enough arguments");
    }

    public NotEnoughArguments(String message) {
        super(message);
    }

}
