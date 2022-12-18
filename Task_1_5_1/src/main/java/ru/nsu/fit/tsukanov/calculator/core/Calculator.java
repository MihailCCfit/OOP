package ru.nsu.fit.tsukanov.calculator.core;

import ru.nsu.fit.tsukanov.calculator.core.Exceptions.CalculatorException;

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
    T calculates() throws CalculatorException;

    /**
     * Calculates result.
     *
     * @param line line for calculation
     * @return result
     * @throws CalculatorException if there is problem
     */
    T calculates(String line) throws CalculatorException;

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
}
