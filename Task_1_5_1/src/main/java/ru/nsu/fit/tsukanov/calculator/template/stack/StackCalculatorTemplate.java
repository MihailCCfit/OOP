package ru.nsu.fit.tsukanov.calculator.template.stack;

import ru.nsu.fit.tsukanov.calculator.core.Calculator;
import ru.nsu.fit.tsukanov.calculator.core.Exceptions.CalculatorException;
import ru.nsu.fit.tsukanov.calculator.core.functions.Function;
import ru.nsu.fit.tsukanov.calculator.core.parser.CalculatorParser;
import ru.nsu.fit.tsukanov.calculator.core.parser.Lexer;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.stream.Collectors;


/**
 * Complex number calculator. Calculates different operations. Template for Calculator.
 * Use prefix notation for input string (or tokens).
 */
public class StackCalculatorTemplate<T> implements Calculator<T> {
    protected final CalculatorParser<T> calculatorParser;
    protected final Lexer lexer;
    protected final Deque<FunctionWrapperStack<T>> functionWrappers = new LinkedList<>();
    protected String line = null;
    protected LinkedList<String> tokens;
    protected T result = null;

    /**
     * Creates stackCalculator, that has stack of functionWrapper. functionWrapper saves arguments
     * while it isn't available.
     */

    public StackCalculatorTemplate(CalculatorParser<T> calculatorParser) {
        this(calculatorParser, string -> string.split("\\s+"));
    }

    public StackCalculatorTemplate(CalculatorParser<T> calculatorParser, Lexer lexer) {
        this.calculatorParser = calculatorParser;
        this.lexer = lexer;
        tokens = new LinkedList<>();
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
        var tok = tokens.removeFirst();
        Function<T> foo = calculatorParser.parse(tok);
        functionWrappers.addLast(new FunctionWrapperStack<>(foo, this));
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

    protected void tokenize() {
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
    public T calculate() throws CalculatorException {
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
    public T calculate(String line) throws CalculatorException {
        newLine(line);
        return calculate();
    }

    /**
     * Calculates result.
     *
     * @return result
     */
    @Override
    public T getResult() {
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
