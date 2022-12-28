package ru.nsu.fit.tsukanov.calculator.complex.functions;

import ru.nsu.fit.tsukanov.calculator.complex.ComplexNumber;
import ru.nsu.fit.tsukanov.calculator.core.functions.Function;

import java.util.List;

public class ComplexSin implements Function<ComplexNumber> {
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
    public ComplexNumber apply(List<ComplexNumber> arguments) {
        ComplexNumber num = arguments.get(0);
        double real = num.real();
        double im = num.imaginary();
        return new ComplexNumber(Math.sin(real) * Math.cosh(im),
                Math.cos(real) * Math.sinh(im));
    }

    /**
     * String representation.
     *
     * @return representation
     */
    @Override
    public String representation() {
        return "sin";
    }
}
