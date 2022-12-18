package ru.nsu.fit.tsukanov.calculator.core.functions;

import ru.nsu.fit.tsukanov.calculator.core.Exceptions.CalculatorException;

public interface FunctionWrapper<T> {
    /**
     * Apply function, depends on (or not) arguments.
     *
     * @param arguments arguments
     * @return number
     */
    T apply(T[] arguments) throws CalculatorException;

    /**
     * Return amount of saved arguments.
     *
     * @return amount of saved arguments
     */

    int numberOfArguments();

    /**
     * Return arity of function.
     *
     * @return arity of function
     */
    int getArity();

    /**
     * String representation.
     *
     * @return representation
     */
    String functionRepresentation();

    /**
     * Get array of current saved arguments.
     *
     * @return array of current arguments.
     */
    T[] curArguments();

    default String representation() {
        return "Arity: " + getArity()
                + "Function: " + functionRepresentation();
    }
}
