package ru.nsu.fit.tsukanov.calculator.core.parser;

import ru.nsu.fit.tsukanov.calculator.core.Exceptions.BadLexemeException;
import ru.nsu.fit.tsukanov.calculator.core.Exceptions.CalculatorException;
import ru.nsu.fit.tsukanov.calculator.core.functions.Function;

public interface NumberParser<T> {
    /**
     * parse token
     *
     * @param token token
     * @return constant function
     */
    Function<T> parseToken(String token) throws BadLexemeException;

    /**
     * Parse to Number.
     *
     * @param token token
     * @return number
     */
    T parseNumber(String token) throws CalculatorException;

    /**
     * Check for number.
     *
     * @param token token
     * @return true if token is number.
     */
    boolean checkNumber(String token);
}
