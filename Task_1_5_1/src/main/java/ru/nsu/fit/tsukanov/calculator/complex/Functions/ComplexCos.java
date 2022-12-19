package ru.nsu.fit.tsukanov.calculator.complex.Functions;

import ru.nsu.fit.tsukanov.calculator.complex.ComplexNumber;
import ru.nsu.fit.tsukanov.calculator.core.functions.Function;

public class ComplexCos implements Function<ComplexNumber> {
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
    public ComplexNumber apply(ComplexNumber[] arguments) {
        ComplexNumber num = arguments[0];
        double real = num.real();
        double im = num.imaginary();
        return new ComplexNumber(Math.cos(real) * Math.cosh(im),
                -1 * Math.sin(real) * Math.sinh(im));
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
