package ru.nsu.fit.tsukanov.calculator.complex.Functions;

import ru.nsu.fit.tsukanov.calculator.complex.ComplexNumber;
import ru.nsu.fit.tsukanov.calculator.core.functions.Function;

public class ComplexMul implements Function<ComplexNumber> {
    /**
     * Return arity of function.
     *
     * @return arity of function
     */
    @Override
    public int getArity() {
        return 2;
    }

    /**
     * Apply function, depends on (or not) arguments.
     *
     * @param arguments arguments
     * @return number
     */
    @Override
    public ComplexNumber apply(ComplexNumber[] arguments) {
        return ComplexNumber.mul(arguments[0], arguments[1]);
    }

    /**
     * String representation.
     *
     * @return representation
     */
    @Override
    public String representation() {
        return "*";
    }
}
