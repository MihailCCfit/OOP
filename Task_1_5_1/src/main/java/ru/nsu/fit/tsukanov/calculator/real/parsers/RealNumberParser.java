package ru.nsu.fit.tsukanov.calculator.real.parsers;

import ru.nsu.fit.tsukanov.calculator.core.Exceptions.BadTokenException;
import ru.nsu.fit.tsukanov.calculator.core.functions.Function;
import ru.nsu.fit.tsukanov.calculator.core.parser.numbers.NumberParserInterface;

import java.util.List;

public class RealNumberParser implements NumberParserInterface<Double> {
    /**
     * Parse token.
     *
     * @param token token
     * @return constant function
     */
    @Override
    public Function<Double> parseToken(String token) throws BadTokenException {
        double res;
        try {
            double mul = 1;
            if (token.charAt(token.length() - 1) == '`') {
                token = token.substring(0, token.length() - 1);
                mul = Math.PI / 180;
            }
            res = Double.parseDouble(token) * mul;
        } catch (NumberFormatException e) {
            throw new BadTokenException(e.getMessage());
        }
        return new Function<>() {
            @Override
            public int getArity() {
                return 0;
            }

            @Override
            public Double apply(List<Double> arguments) {
                return res;
            }

            @Override
            public String representation() {
                return Double.toString(res);
            }
        };
    }

    /**
     * Parse to Number.
     *
     * @param token token
     * @return number
     */
    @Override
    public Double parseNumber(String token) throws BadTokenException {
        return parseToken(token).apply(List.of());
    }

    /**
     * Check for number.
     *
     * @param token token
     * @return true if token is number.
     */
    @Override
    public boolean checkNumber(String token) {
        try {
            parseToken(token);
            return true;
        } catch (BadTokenException e) {
            return false;
        }
    }
}
