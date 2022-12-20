package ru.nsu.fit.tsukanov.calculator.core.functions;

import java.util.List;

public interface Function<T> {
    /**
     * Return arity of function.
     *
     * @return arity of function
     */
    int getArity();

    /**
     * Apply function, depends on (or not) arguments.
     *
     * @param arguments arguments
     * @return number
     */
    T apply(List<T> arguments);

    /**
     * String representation.
     *
     * @return representation
     */
    String representation();

}
