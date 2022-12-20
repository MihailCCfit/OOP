package ru.nsu.fit.tsukanov.calculator.core.functions;

import ru.nsu.fit.tsukanov.calculator.core.Exceptions.CalculatorException;

public interface FunctionWrapper<T> {
    /**
     * Apply function, depends on (or not) arguments.
     *
     * @return number
     */
    T apply() throws CalculatorException;


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


    default String representation() {
        return "Arity: " + getArity()
                + "Function: " + functionRepresentation();
    }

    boolean available();
}
