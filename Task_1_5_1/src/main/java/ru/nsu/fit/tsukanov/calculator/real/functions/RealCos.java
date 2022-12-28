package ru.nsu.fit.tsukanov.calculator.real.functions;

import ru.nsu.fit.tsukanov.calculator.core.functions.Function;

import java.util.List;

public class RealCos implements Function<Double> {
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
     *
     * @param arguments arguments
     * @return number
     */
    @Override
    public Double apply(List<Double> arguments) {
        Double num = arguments.get(0);
        return Math.cos(num);
    }

    /**
     * String representation.
     *
     * @return representation
     */
    @Override
    public String representation() {
        return "cos";
    }
}
