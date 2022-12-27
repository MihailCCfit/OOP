package ru.nsu.fit.tsukanov.calculator.template.stack;

import ru.nsu.fit.tsukanov.calculator.core.Calculator;
import ru.nsu.fit.tsukanov.calculator.core.Exceptions.CalculatorException;
import ru.nsu.fit.tsukanov.calculator.core.Exceptions.NotEnoughArguments;
import ru.nsu.fit.tsukanov.calculator.core.functions.AbstractFunctionWrapper;
import ru.nsu.fit.tsukanov.calculator.core.functions.Function;

import java.util.LinkedList;

public class FunctionWrapperStack<T> extends AbstractFunctionWrapper<T> {

    private final LinkedList<T> arguments = new LinkedList<>();

    /**
     * Adds number (argument) to the end of arguments list .
     *
     * @param number the number that will be added to the list of arguments.
     * @throws CalculatorException if there is problem with argument
     */
    public void addArg(T number) throws CalculatorException {
        if (number == null) {
            throw new CalculatorException("Complex number is null");
        }
        if (arguments.size() >= getArity()) {
            throw new CalculatorException("Too much arguments");
        }
        arguments.addLast(number);
    }

    public FunctionWrapperStack(Function<T> function, Calculator<T> calculator) {
        super(function, calculator);
    }

    /**
     * Verifies that a function can be applied.
     *
     * @return true if function can be applied
     */
    public boolean available() {
        return arguments.size() == getArity();
    }

    /**
     * Apply function, depends on (or not) arguments.
     *
     * @return number
     */
    @Override
    public T apply() throws CalculatorException {
        if (arguments.size() < getArity()) {
            throw new NotEnoughArguments("For function: {" + function.representation() + "} Needs:" + getArity()
                    + "| Current:" + arguments.size() + " args: " + arguments);
        }
        return function.apply(arguments);
    }


    @Override
    public String toString() {
        return representation();
    }
}
