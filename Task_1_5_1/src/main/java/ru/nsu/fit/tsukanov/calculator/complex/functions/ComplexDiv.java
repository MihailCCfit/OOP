package ru.nsu.fit.tsukanov.calculator.complex.functions;

import ru.nsu.fit.tsukanov.calculator.complex.ComplexNumber;
import ru.nsu.fit.tsukanov.calculator.core.functions.Function;

import java.util.List;

public class ComplexDiv implements Function<ComplexNumber> {

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
    public ComplexNumber apply(List<ComplexNumber> arguments) {
        return ComplexNumber.div(arguments.get(0), arguments.get(1));
    }

    /**
     * String representation.
     *
     * @return representation
     */
    @Override
    public String representation() {
        return "/";
    }
}
