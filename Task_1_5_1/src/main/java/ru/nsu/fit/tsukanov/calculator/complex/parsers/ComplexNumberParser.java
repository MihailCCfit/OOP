package ru.nsu.fit.tsukanov.calculator.complex.parsers;

import ru.nsu.fit.tsukanov.calculator.complex.ComplexNumber;
import ru.nsu.fit.tsukanov.calculator.core.Exceptions.BadTokenException;
import ru.nsu.fit.tsukanov.calculator.core.functions.Function;
import ru.nsu.fit.tsukanov.calculator.core.parser.numbers.NumberParser;

import java.util.ArrayList;
import java.util.List;

public class ComplexNumberParser extends NumberParser<ComplexNumber> {

    /**
     * parse token
     *
     * @param token token
     * @return constant function
     */
    @Override
    public Function<ComplexNumber> parseToken(String token) throws BadTokenException {
        if (token == null) {
            throw new BadTokenException("Null");
        }
        if (token.isEmpty()) {
            throw new BadTokenException("Empty");
        }
        if (token.charAt(0) != '(' || token.charAt(token.length() - 1) != ')') {
            throw new BadTokenException(token);
        }
        token = token.substring(1, token.length() - 1);
        String[] splited = token.split(",", 0);
        if (splited.length != 2) {
            throw new BadTokenException(token);
        }
        var realStr = splited[0];
        var imagStr = splited[1];
        double real;
        double imag;
        boolean rFlag = false;
        boolean iFlag = false;
        if (realStr.charAt(realStr.length() - 1) == '`') {
            rFlag = true;
            realStr = realStr.substring(0, realStr.length() - 1);
        }
        if (imagStr.charAt(imagStr.length() - 1) == '`') {
            iFlag = true;
            imagStr = imagStr.substring(0, imagStr.length() - 1);
        }
        try {
            real = Double.parseDouble(realStr);
            if (rFlag) {
                real = real * Math.PI / 180;
            }
            imag = Double.parseDouble(imagStr);
            if (iFlag) {
                imag = imag * Math.PI / 180;
            }
        } catch (NumberFormatException e) {
            throw new BadTokenException(token);
        }
        double finalImag = imag;
        double finalReal = real;
        return new Function<>() {
            @Override
            public int getArity() {
                return 0;
            }

            @Override
            public ComplexNumber apply(List<ComplexNumber> arguments) {
                return new ComplexNumber(finalReal, finalImag);
            }

            @Override
            public String representation() {
                return new ComplexNumber(finalReal, finalImag).toString();
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
        try {
            parseToken(token);
            return true;
        } catch (BadTokenException e) {
            return false;
        }
    }
}
