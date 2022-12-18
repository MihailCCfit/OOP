package ru.nsu.fit.tsukanov.calculator.complex;

import ru.nsu.fit.tsukanov.calculator.complex.parsers.ComplexFunctionParser;
import ru.nsu.fit.tsukanov.calculator.complex.parsers.ComplexNumberParser;
import ru.nsu.fit.tsukanov.calculator.core.Calculator;
import ru.nsu.fit.tsukanov.calculator.core.Exceptions.CalculatorException;
import ru.nsu.fit.tsukanov.calculator.core.functions.Function;
import ru.nsu.fit.tsukanov.calculator.core.parser.CalculatorParser;
import ru.nsu.fit.tsukanov.calculator.core.parser.Lexer;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

public class StackCalculator implements Calculator<ComplexNumber> {
    CalculatorParser<ComplexNumber> calculatorParser;
    Lexer lexer;
    Deque<ComplexFunctionWrapper> functionWrappers = new LinkedList<>();
    String line = null;
    LinkedList<String> tokens = null;
    ComplexNumber result = null;

    public StackCalculator() {
        var complexFunctionParser = ComplexFunctionParser.getParser();
        var complexNumberParser = new ComplexNumberParser();
        calculatorParser = new CalculatorParser<>(complexNumberParser, complexFunctionParser);
        lexer = string -> string.split(" ");
    }

    public boolean next() throws CalculatorException {
        if (tokens.isEmpty()) {
            return true;
        }
        Function<ComplexNumber> foo = calculatorParser.parse(tokens.removeFirst());
        functionWrappers.addLast(new ComplexFunctionWrapper(foo, this));
        while (functionWrappers.peekLast().available()) {
            var availableFunction = functionWrappers.removeLast();
            if (functionWrappers.isEmpty()) {
                functionWrappers.add(availableFunction);
                return true;
            }
            functionWrappers.peekLast().addArg(availableFunction.apply());
        }
        return false;

    }

    private void tokenize() {
        tokens = new LinkedList<>(Arrays.stream(lexer.getTokens(line)).toList());
    }

    /**
     * Change line for calculating.
     *
     * @param input line for parsing
     * @return old input
     */
    @Override
    public String newLine(String input) {
        if (input == null) {
            throw new NullPointerException();
        }
        String old = line;
        line = input;
        tokenize();
        functionWrappers.clear();
        result = null;
        return old;
    }

    /**
     * Calculates result.
     *
     * @return result
     * @throws CalculatorException if there is problem
     */
    @Override
    public ComplexNumber calculates() throws CalculatorException {
        tokenize();
        while (true) {
            if (next()) {
                break;
            }
        }
        if (tokens.size() != 0) {
            throw new CalculatorException("Result:" + result + "There are not parsed tokens: " + tokens);
        }
        if (functionWrappers.peek() == null) {
            throw new CalculatorException("There is no result with: " + functionWrappers);
        }
        result = functionWrappers.peek().apply();
        return result;
    }

    /**
     * Calculates result.
     *
     * @param line line for calculation
     * @return result
     * @throws CalculatorException if there is problem
     */
    @Override
    public ComplexNumber calculates(String line) throws CalculatorException {
        newLine(line);
        return calculates();
    }

    /**
     * Calculates result.
     *
     * @return result
     */
    @Override
    public ComplexNumber getResult() {
        return result;
    }


    /**
     * Get info about state of calculator.
     *
     * @return information
     */
    @Override
    public String getInformation() {
        return "Result: " + result + " Tokens" + tokens + "Functions: " + functionWrappers;
    }

    @Override
    public String toString() {
        return "StackCalculator{" +
                "calculatorParser=" + calculatorParser +
                ", lexer=" + lexer +
                ", functionWrappers=" + functionWrappers +
                ", line='" + line + '\'' +
                ", tokens=" + tokens +
                ", result=" + result +
                '}';
    }
}
