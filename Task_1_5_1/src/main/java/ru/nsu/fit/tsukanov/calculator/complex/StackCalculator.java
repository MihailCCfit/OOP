package ru.nsu.fit.tsukanov.calculator.complex;

import ru.nsu.fit.tsukanov.calculator.complex.parsers.ComplexFunctionParser;
import ru.nsu.fit.tsukanov.calculator.core.Calculator;
import ru.nsu.fit.tsukanov.calculator.core.Exceptions.CalculatorException;
import ru.nsu.fit.tsukanov.calculator.core.functions.AbstractFunctionWrapper;
import ru.nsu.fit.tsukanov.calculator.core.parser.CalculatorParser;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class StackCalculator implements Calculator<ComplexNumber> {
    CalculatorParser<ComplexNumber> calculatorParser;
    Deque<AbstractFunctionWrapper<ComplexNumber>> functionWrappers = new ArrayDeque<>();
    String line;
    List<String> tokens;

    public StackCalculator() {
        ComplexFunctionParser.getParser();
    }


    /**
     * Change line for calculating.
     *
     * @param input line for parsing
     * @return old input
     */
    @Override
    public String newLine(String input) {
        return null;
    }

    /**
     * Calculates result.
     *
     * @return result
     * @throws CalculatorException if there is problem
     */
    @Override
    public ComplexNumber getResult() throws CalculatorException {
        return null;
    }

    /**
     * Parse and calculates line
     *
     * @param line line for parsing
     * @return result
     * @throws CalculatorException if there is problem
     */
    @Override
    public ComplexNumber getResult(String line) throws CalculatorException {
        return null;
    }

    /**
     * Get info about state of calculator.
     *
     * @return information
     */
    @Override
    public String getInformation() {
        return null;
    }
}
