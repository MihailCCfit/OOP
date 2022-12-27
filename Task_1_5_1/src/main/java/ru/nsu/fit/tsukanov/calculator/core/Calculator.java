package ru.nsu.fit.tsukanov.calculator.core;

import ru.nsu.fit.tsukanov.calculator.core.Exceptions.CalculatorException;
import ru.nsu.fit.tsukanov.calculator.core.functions.Function;
import ru.nsu.fit.tsukanov.calculator.core.parser.numbers.NumberParserInterface;

public interface Calculator<T> {
    /**
     * Change line for calculating.
     *
     * @param input line for parsing
     * @return old input
     */
    String newLine(String input);

    /**
     * Calculates result.
     *
     * @return result
     * @throws CalculatorException if there is problem
     */
    T calculate() throws CalculatorException;

    /**
     * Calculates result.
     *
     * @param line line for calculation
     * @return result
     * @throws CalculatorException if there is problem
     */
    T calculate(String line) throws CalculatorException;

    /**
     * Return prev result.
     *
     * @return result
     */
    T getResult();


    /**
     * Get info about state of calculator.
     *
     * @return information
     */
    String getInformation();

    default boolean addToParser(NumberParserInterface<T> numberParser) {
        throw new UnsupportedOperationException("addToParser not implemented");
    }

    default boolean addToParser(Function<T> function) {
        throw new UnsupportedOperationException("addToParser not implemented");
    }
}
