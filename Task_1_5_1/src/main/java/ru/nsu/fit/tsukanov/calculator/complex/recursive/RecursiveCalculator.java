package ru.nsu.fit.tsukanov.calculator.complex.recursive;

import ru.nsu.fit.tsukanov.calculator.complex.ComplexNumber;
import ru.nsu.fit.tsukanov.calculator.complex.parsers.ComplexFunctionParser;
import ru.nsu.fit.tsukanov.calculator.complex.parsers.ComplexNumberParser;
import ru.nsu.fit.tsukanov.calculator.core.Calculator;
import ru.nsu.fit.tsukanov.calculator.core.Exceptions.CalculatorException;
import ru.nsu.fit.tsukanov.calculator.core.parser.CalculatorParser;
import ru.nsu.fit.tsukanov.calculator.core.parser.Lexer;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class RecursiveCalculator implements Calculator<ComplexNumber> {
    private final CalculatorParser<ComplexNumber> calculatorParser;
    private final Lexer lexer;
    private String line = null;
    private LinkedList<String> tokens;
    private ComplexNumber result = null;

    /**
     * Creates stackCalculator, that has stack of functionWrapper. functionWrapper saves arguments
     * while it isn't available.
     */

    public RecursiveCalculator() {
        this(new CalculatorParser<>(new ComplexNumberParser(), ComplexFunctionParser.getParser()));
    }

    /**
     * Creates stackCalculator, that has stack of functionWrapper.
     *
     * @param calculatorParser the parser for parsing tokens
     */
    public RecursiveCalculator(CalculatorParser<ComplexNumber> calculatorParser) {
        this(calculatorParser, string -> string.split("\\s+"));
    }

    /**
     * Creates stackCalculator, that has stack of functionWrapper.
     *
     * @param calculatorParser the parser for parsing tokens
     * @param lexer            for tokenize
     */
    public RecursiveCalculator(CalculatorParser<ComplexNumber> calculatorParser, Lexer lexer) {
        this.calculatorParser = calculatorParser;
        this.lexer = lexer;
        tokens = new LinkedList<>();
    }


    /**
     * Calculates next token. It's used by function wrappers.
     *
     * @return ComplexNumber
     * @throws CalculatorException if there is some exception to parsing of calculating, or bad input.
     */

    ComplexNumber next() throws CalculatorException {
        if (tokens.isEmpty()) {
            throw new CalculatorException("No another tokens" + getInformation());
        }
        return new RecursiveWrapper(calculatorParser.parse(tokens.removeFirst()), this).apply();
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
    public ComplexNumber calculate() throws CalculatorException {
        result = next();
        if (tokens.size() != 0) {
            throw new CalculatorException("Result:" + result + "There are not parsed tokens: " + tokens);
        }

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
    public ComplexNumber calculate(String line) throws CalculatorException {
        newLine(line);
        return calculate();
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
        return "Result: " + result + " Tokens" + tokens + "Functions: ";
    }

    @Override
    public String toString() {
        return "StackCalculator{" +
                "calculatorParser=" + calculatorParser +
                ", lexer=" + lexer +
                ", line='" + line + '\'' +
                ", tokens=" + tokens +
                ", result=" + result +
                '}';
    }


}
