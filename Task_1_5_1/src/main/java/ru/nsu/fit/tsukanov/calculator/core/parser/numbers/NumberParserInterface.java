package ru.nsu.fit.tsukanov.calculator.core.parser.numbers;

import ru.nsu.fit.tsukanov.calculator.core.Exceptions.BadTokenException;
import ru.nsu.fit.tsukanov.calculator.core.functions.Function;

import java.util.List;

public interface NumberParserInterface<T> {
    /**
     * Parse token.
     *
     * @param token token
     * @return constant function
     */
    default Function<T> parseToken(String token) throws BadTokenException {
        var num = parseNumber(token);
        return new Function<>() {
            @Override
            public int getArity() {
                return 0;
            }

            @Override
            public T apply(List<T> arguments) {
                return num;
            }

            @Override
            public String representation() {
                return num.toString();
            }
        };
    }

    /**
     * Parse to Number.
     *
     * @param token token
     * @return number
     */
    T parseNumber(String token) throws BadTokenException;

    /**
     * Check for number.
     *
     * @param token token
     * @return true if token is number.
     */
    default boolean checkNumber(String token) {
        try {
            parseToken(token);
            return true;
        } catch (BadTokenException e) {
            return false;
        }
    }
}
