package ru.nsu.fit.tsukanov.calculator.complex;

import ru.nsu.fit.tsukanov.calculator.complex.parsers.ComplexFunctionParser;
import ru.nsu.fit.tsukanov.calculator.complex.parsers.ComplexNumberParser;
import ru.nsu.fit.tsukanov.calculator.core.Calculator;
import ru.nsu.fit.tsukanov.calculator.core.Exceptions.BadLexemeException;
import ru.nsu.fit.tsukanov.calculator.core.Exceptions.CalculatorException;
import ru.nsu.fit.tsukanov.calculator.core.functions.Function;
import ru.nsu.fit.tsukanov.calculator.core.parser.CalculatorParser;
import ru.nsu.fit.tsukanov.calculator.core.parser.Lexer;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * Complex number calculator. Calculates different operations.
 */
public class StackCalculator implements Calculator<ComplexNumber> {
    private final CalculatorParser<ComplexNumber> calculatorParser;
    private final Lexer lexer;
    private final Deque<ComplexFunctionWrapper> functionWrappers = new LinkedList<>();
    private String line = null;
    private LinkedList<String> tokens;
    private ComplexNumber result = null;

    /**
     * Creates stackCalculator, that has stack of functionWrapper. functionWrapper saves arguments
     * while it isn't available.
     */

    public StackCalculator() {
        var complexFunctionParser = ComplexFunctionParser.getParser();
        var complexNumberParser = new ComplexNumberParser();
        calculatorParser = new CalculatorParser<>(complexNumberParser, complexFunctionParser);
        lexer = string -> string.split(" ");
        tokens = new LinkedList<>();
    }

    /**
     * Add token to the end of tokens.
     *
     * @param token token (function or number)
     * @throws BadLexemeException if there is problem with parsing
     */
    public void addToken(String token) throws CalculatorException {
        result = null;
        calculatorParser.parse(token);
        tokens.addLast(token);
        next();
    }

    /**
     * Clear all tokens, stack and result.
     * @return old result
     */

    public ComplexNumber clear(){
        var old = result;
        result = null;
        tokens.clear();
        functionWrappers.clear();
        line = null;
        return old;
    }


    /**
     * Calculates next token. It's useful to use this after toke appending.
     *
     * @return true if next {@code next} will do nothing. In correct state it signalized that
     * result was calculated. So check result
     * @throws CalculatorException if there is some exception to parsing of calculating, or bad input.
     */

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

    /**
     * Tokenize input string to the tokens. Currently just separate by the space.
     */

    private void tokenize() {
        tokens = Arrays.stream(lexer.getTokens(line)).collect(Collectors.toCollection(LinkedList::new));
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
