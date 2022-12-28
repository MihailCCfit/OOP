package ru.nsu.fit.tsukanov.calculator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.fit.tsukanov.calculator.complex.ComplexNumber;
import ru.nsu.fit.tsukanov.calculator.complex.recursive.RecursiveCalculator;
import ru.nsu.fit.tsukanov.calculator.complex.recursive.RecursiveWrapper;
import ru.nsu.fit.tsukanov.calculator.core.Exceptions.BadTokenException;
import ru.nsu.fit.tsukanov.calculator.core.functions.Function;
import ru.nsu.fit.tsukanov.calculator.core.parser.numbers.NumberParser;
import ru.nsu.fit.tsukanov.calculator.core.parser.numbers.NumberParserInterface;

import java.util.List;

public class OtherTests {
    @Test
    void equalTest() {
        ComplexNumber number1 = new ComplexNumber(0, 0);
        Assertions.assertTrue(number1.equals(number1));
        Assertions.assertFalse(number1.equals(""));
        Assertions.assertFalse(number1.equals(new ComplexNumber(0, 1)));
        Assertions.assertFalse(number1.equals(new ComplexNumber(1, 0)));
        RecursiveWrapper wrapper = new RecursiveWrapper(new Function<>() {
            @Override
            public int getArity() {
                return 0;
            }

            @Override
            public ComplexNumber apply(List<ComplexNumber> arguments) {
                return new ComplexNumber(0, 0);
            }

            @Override
            public String representation() {
                return new ComplexNumber(0, 0).toString();
            }
        }, new RecursiveCalculator());
        Assertions.assertTrue(wrapper.available());
        NumberParser<Object> numberParser = new NumberParser<>();
        numberParser.putNumberParser(new NumberParserInterface<>() {
            @Override
            public Object parseNumber(String token) throws BadTokenException {
                throw new BadTokenException();
            }

            @Override
            public boolean checkNumber(String token) {
                return true;
            }
        });
        Assertions.assertThrows(BadTokenException.class, () -> numberParser.parseNumber(""));
    }
}
