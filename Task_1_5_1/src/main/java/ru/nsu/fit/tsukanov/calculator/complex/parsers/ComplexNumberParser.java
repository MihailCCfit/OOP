package ru.nsu.fit.tsukanov.calculator.complex.parsers;

import ru.nsu.fit.tsukanov.calculator.complex.ComplexNumber;
import ru.nsu.fit.tsukanov.calculator.core.Exceptions.BadLexemeException;
import ru.nsu.fit.tsukanov.calculator.core.Exceptions.CalculatorException;
import ru.nsu.fit.tsukanov.calculator.core.functions.Function;
import ru.nsu.fit.tsukanov.calculator.core.parser.NumberParser;

import java.util.ArrayList;
import java.util.List;

public class ComplexNumberParser implements NumberParser<ComplexNumber> {

    /**
     * parse token
     *
     * @param token token
     * @return constant function
     */
    @Override
    public Function<ComplexNumber> parseToken(String token) throws BadLexemeException {
        if (token == null) {
            throw new BadLexemeException("Null");
        }
        if (token.isEmpty()) {
            throw new BadLexemeException("Empty");
        }
        if (token.charAt(0) != '(' || token.charAt(token.length() - 1) != ')') {
            throw new BadLexemeException(token);
        }
        token = token.substring(1, token.length() - 1);
        String[] splited = token.split(",", 0);
        if (splited.length != 2) {
            throw new BadLexemeException(token);
        }
        var realStr = splited[0];
        var imagStr = splited[1];
        double real;
        double imag;
        try {
            real = Double.parseDouble(realStr);
            imag = Double.parseDouble(imagStr);
        } catch (NumberFormatException e) {
            throw new BadLexemeException(token);
        }
        return new Function<>() {
            @Override
            public int getArity() {
                return 0;
            }

            @Override
            public ComplexNumber apply(List<ComplexNumber> arguments) {
                return new ComplexNumber(real, imag);
            }

            @Override
            public String representation() {
                return new ComplexNumber(real, imag).toString();
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
    public ComplexNumber parseNumber(String token) throws CalculatorException {
        return parseToken(token).apply(new ArrayList<>());
    }

    /**
     * Check for number.
     *
     * @param token token
     * @return true if token is number.
     */
    @Override
    public boolean checkNumber(String token) {
        if (token == null) {
            return false;
        }
        if (token.isEmpty()) {
            return false;
        }
        if (token.charAt(0) != '(' || token.charAt(token.length() - 1) != ')') {
            return false;
        }
        token = token.substring(1, token.length() - 1);
        String[] splitted = token.split(",", 0);
        if (splitted.length != 2) {
            return false;
        }
        var realStr = splitted[0];
        var imagStr = splitted[1];
        try {
            Double.parseDouble(realStr);
            Double.parseDouble(imagStr);
        } catch (NumberFormatException e) {
            return false;
        }


        return true;
    }
}
