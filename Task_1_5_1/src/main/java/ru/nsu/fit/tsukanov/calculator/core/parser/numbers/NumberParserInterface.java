package ru.nsu.fit.tsukanov.calculator.core.parser.numbers;

import ru.nsu.fit.tsukanov.calculator.core.Exceptions.BadLexemeException;
import ru.nsu.fit.tsukanov.calculator.core.functions.Function;

public interface NumberParserInterface<T> {
    /**
     * Parse token.
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
    T parseNumber(String token) throws BadLexemeException;

    /**
     * Check for number.
     *
     * @param token token
     * @return true if token is number.
     */
    boolean checkNumber(String token);
}
