package ru.nsu.fit.tsukanov.calculator.core.functions;

import ru.nsu.fit.tsukanov.calculator.core.Exceptions.NotNumberException;

public interface ConverterNumToFun<T> {
    /**
     * Convert constant function to  number.
     *
     * @param function zero-arity function that means constant-function.
     * @return number
     * @throws NotNumberException if it's not number
     */
    T toNum(Function<T> function) throws NotNumberException;

    /**
     * Convert number to constant function.
     *
     * @param number just number
     * @return zero-arity function that means constant-function.
     * @throws NotNumberException if it's not number
     */
    Function<T> toFunction(T number) throws NotNumberException;
}
