package ru.nsu.fit.tsukanov.calculator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.fit.tsukanov.calculator.core.Exceptions.BadTokenException;
import ru.nsu.fit.tsukanov.calculator.core.Exceptions.CalculatorException;
import ru.nsu.fit.tsukanov.calculator.core.functions.Function;
import ru.nsu.fit.tsukanov.calculator.core.parser.functions.FunctionParser;
import ru.nsu.fit.tsukanov.calculator.real.RealCalculator;
import ru.nsu.fit.tsukanov.calculator.real.parsers.RealNumberParser;

import java.util.List;

public class RealCalculatorTest {
    @Test
    void test() {
        RealCalculator calculator = new RealCalculator();
        var funn = new Function<Double>() {
            @Override
            public int getArity() {
                return 0;
            }

            @Override
            public Double apply(List<Double> arguments) {
                return Math.PI;
            }

            @Override
            public String representation() {
                return "pi";
            }
        };
        calculator.addToParser(funn);
        calculator.addToParser(funn);
        try {
            Assertions.assertEquals(0, calculator.calculate("tan ln / * ^ sqrt cos sin + - 3 3 pi 5 2 2"));
            Assertions.assertEquals(1, calculator.calculate("sin 90`"));
            Assertions.assertThrows(BadTokenException.class, () -> calculator.calculate("sin 90'"));
            RealNumberParser numberParser = new RealNumberParser();
            var fun = numberParser.parseToken("90`");
            Assertions.assertEquals(0, fun.getArity());
            Assertions.assertFalse(fun.representation().isEmpty());
            calculator.newLine("+ 1");
            Assertions.assertThrows(CalculatorException.class, calculator::calculate);
            FunctionParser parser = new FunctionParser();
            Assertions.assertThrows(BadTokenException.class, () -> parser.parseToken("152"));

        } catch (CalculatorException e) {
            throw new RuntimeException(e);
        }
    }
}
