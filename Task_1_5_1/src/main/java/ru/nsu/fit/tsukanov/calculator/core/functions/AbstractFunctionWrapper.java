package ru.nsu.fit.tsukanov.calculator.core.functions;

import ru.nsu.fit.tsukanov.calculator.core.Calculator;
import ru.nsu.fit.tsukanov.calculator.core.Exceptions.CalculatorException;

public abstract class AbstractFunctionWrapper<T> implements FunctionWrapper<T> {

    protected Function<T> function;
    protected Calculator<T> calculator;

    public AbstractFunctionWrapper(Function<T> function, Calculator<T> calculator) {
        this.function = function;
        this.calculator = calculator;
    }

    /**
     * Apply function, depends on (or not) arguments.
     *
     * @return number
     */
    @Override
    public abstract T apply() throws CalculatorException;


    /**
     * Return arity of function.
     *
     * @return arity of function
     */
    @Override
    public int getArity() {
        return function.getArity();
    }

    /**
     * String representation.
     *
     * @return representation
     */
    @Override
    public String functionRepresentation() {
        return function.representation();
    }


}
