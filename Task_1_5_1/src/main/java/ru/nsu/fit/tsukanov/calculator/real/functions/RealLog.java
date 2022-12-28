package ru.nsu.fit.tsukanov.calculator.real.functions;

import ru.nsu.fit.tsukanov.calculator.core.functions.Function;

import java.util.List;

public class RealLog implements Function<Double> {
    /**
     * Return arity of function.
     *
     * @return arity of function
     */
    @Override
    public int getArity() {
        return 1;
    }

    /**
     * Apply function, depends on (or not) arguments.
     * Get natural logarithm.
     *
     * @param arguments arguments
     * @return number
     */
    @Override
    public Double apply(List<Double> arguments) {
        return Math.log(arguments.get(0));
    }

    /**
     * String representation.
     *
     * @return representation
     */
    @Override
    public String representation() {
        return "ln";
    }
}
