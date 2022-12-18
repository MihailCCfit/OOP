package ru.nsu.fit.tsukanov.calculator.complex;

import ru.nsu.fit.tsukanov.calculator.core.Calculator;
import ru.nsu.fit.tsukanov.calculator.core.Exceptions.CalculatorException;

public class RecursiveCalculator<T> implements Calculator<T> {

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
    public T getResult() throws CalculatorException {
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
    public T getResult(String line) throws CalculatorException {
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
