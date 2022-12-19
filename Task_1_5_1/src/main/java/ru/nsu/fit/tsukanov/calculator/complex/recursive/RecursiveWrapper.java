package ru.nsu.fit.tsukanov.calculator.complex.recursive;

import ru.nsu.fit.tsukanov.calculator.complex.ComplexNumber;
import ru.nsu.fit.tsukanov.calculator.core.Calculator;
import ru.nsu.fit.tsukanov.calculator.core.Exceptions.CalculatorException;
import ru.nsu.fit.tsukanov.calculator.core.functions.AbstractFunctionWrapper;
import ru.nsu.fit.tsukanov.calculator.core.functions.Function;
import ru.nsu.fit.tsukanov.calculator.core.functions.FunctionWrapper;

import java.util.ArrayList;
import java.util.List;

public class RecursiveWrapper extends AbstractFunctionWrapper<ComplexNumber> {
    private final List<ComplexNumber> arguments = new ArrayList<>();
    private final RecursiveCalculator calculator;

    public RecursiveWrapper(Function<ComplexNumber> function, RecursiveCalculator calculator){
        super(function, calculator);
        this.calculator = calculator;
    }
    /**
     * Apply function, depends on (or not) arguments. Uses calculator for getting arguments.
     *
     * @return number
     */
    @Override
    public ComplexNumber apply() throws CalculatorException {
        for (int i = 0; i < getArity(); i++) {
            arguments.add(calculator.next());
        }
        return function.apply(arguments.toArray(new ComplexNumber[getArity()]));
    }
}
