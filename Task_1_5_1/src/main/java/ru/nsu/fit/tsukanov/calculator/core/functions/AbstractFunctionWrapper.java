package ru.nsu.fit.tsukanov.calculator.core.functions;

import ru.nsu.fit.tsukanov.calculator.core.Calculator;
import ru.nsu.fit.tsukanov.calculator.core.Exceptions.CalculatorException;

import java.util.List;

public abstract class AbstractFunctionWrapper<T> implements FunctionWrapper<T> {

    protected List<T> arguments;
    protected Function<T> function;
    protected Calculator<T> calculator;

    public AbstractFunctionWrapper(Function<T> function, Calculator<T> calculator) {
        this.function = function;
        this.calculator = calculator;
    }

    /**
     * Apply function, depends on (or not) arguments.
     *
     * @param arguments arguments
     * @return number
     */
    @Override
    public abstract T apply(T[] arguments) throws CalculatorException;

    /**
     * Return amount of saved arguments.
     *
     * @return amount of saved arguments
     */
    @Override
    public int numberOfArguments() {
        return arguments.size();
    }

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

    /**
     * Get array of current saved arguments.
     *
     * @return array of current arguments.
     */
    @Override
    public T[] curArguments() {
        return (T[]) arguments.toArray();
    }


}
