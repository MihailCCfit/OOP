package ru.nsu.fit.tsukanov.calculator.complex.functions;

import ru.nsu.fit.tsukanov.calculator.complex.ComplexNumber;
import ru.nsu.fit.tsukanov.calculator.core.functions.Function;

import java.util.List;

public class ComplexLog implements Function<ComplexNumber> {
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
    public ComplexNumber apply(List<ComplexNumber> arguments) {
        ComplexNumber number = arguments.get(0);
        double x = number.real();
        double y = number.imaginary();
        var r = number.module();
        var real = Math.log(r);
        double im = 0;
        if (x < 0 && y < 0) {
            im = Math.atan(y / x) - Math.PI;
        }
        if (x >= 0) {
            im = Math.atan(number.imaginary() / number.real());
        }
        if (x < 0 && y >= 0) {
            im = Math.atan(y / x) + Math.PI;
        }

        return new ComplexNumber(real, im);
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
