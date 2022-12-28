package ru.nsu.fit.tsukanov.calculator.complex.parsers;

import ru.nsu.fit.tsukanov.calculator.complex.ComplexNumber;
import ru.nsu.fit.tsukanov.calculator.core.Exceptions.BadTokenException;
import ru.nsu.fit.tsukanov.calculator.core.functions.Function;
import ru.nsu.fit.tsukanov.calculator.core.parser.numbers.NumberParserInterface;

import java.util.List;

public class RealNumberParser implements NumberParserInterface<ComplexNumber> {
    /**
     * Parse token.
     *
     * @param token token
     * @return constant function
     */
    @Override
    public Function<ComplexNumber> parseToken(String token) throws BadTokenException {
        double res;
        try {
            res = Double.parseDouble(token);
        }
        catch (NumberFormatException e){
            throw new BadTokenException(e.getMessage());
        }
        return new Function<>() {
            @Override
            public int getArity() {
                return 0;
            }

            @Override
            public ComplexNumber apply(List<ComplexNumber> arguments) {
                return new ComplexNumber(res, 0);
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
    public ComplexNumber parseNumber(String token) throws BadTokenException {
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
