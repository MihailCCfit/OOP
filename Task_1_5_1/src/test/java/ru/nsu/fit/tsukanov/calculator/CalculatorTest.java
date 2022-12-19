package ru.nsu.fit.tsukanov.calculator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.fit.tsukanov.calculator.complex.ComplexFunctionWrapper;
import ru.nsu.fit.tsukanov.calculator.complex.ComplexNumber;
import ru.nsu.fit.tsukanov.calculator.complex.StackCalculator;
import ru.nsu.fit.tsukanov.calculator.complex.parsers.ComplexNumberParser;
import ru.nsu.fit.tsukanov.calculator.core.Exceptions.*;
import ru.nsu.fit.tsukanov.calculator.core.functions.Function;

import java.util.Arrays;
import java.util.Comparator;

public class CalculatorTest {
    /**
     * Test calculator calculating with valid and invalid input.
     *
     * @throws CalculatorException if there is problem (can't be)
     */
    @Test
    void test() throws CalculatorException {
        StackCalculator stackCalculator = new StackCalculator();
        stackCalculator.newLine("+ (1,2) (2,5)");
        try {
            Assertions.assertEquals(stackCalculator.calculates(), new ComplexNumber(3, 7));
            System.out.println(stackCalculator.getInformation());
            Assertions.assertEquals(stackCalculator.getResult(), new ComplexNumber(3, 7));
            Assertions.assertEquals(stackCalculator.calculates("- (3,2) (2,3)"),
                    new ComplexNumber(1, -1));
            Assertions.assertEquals(stackCalculator.calculates("* (2,0) (2,3)"),
                    new ComplexNumber(4, 6));
            Assertions.assertEquals(stackCalculator.calculates("/ (4,3) (2,0)"),
                    new ComplexNumber(2, 1.5));
            Assertions.assertEquals(stackCalculator.calculates("ln (1,0)"),
                    new ComplexNumber(0, 0));
            Assertions.assertEquals(stackCalculator.calculates("ln (0,1)"),
                    new ComplexNumber(0, Math.PI / 2));
            Assertions.assertEquals(stackCalculator.calculates("ln (0,-1)"),
                    new ComplexNumber(0, -Math.PI / 2));
            Assertions.assertEquals(stackCalculator.calculates("ln (1,1)"),
                    new ComplexNumber(Math.log(Math.sqrt(2)), Math.PI / 4));
            Assertions.assertEquals(stackCalculator.calculates("ln (-1,-1)"),
                    new ComplexNumber(Math.log(Math.sqrt(2)), -3 * Math.PI / 4));

        } catch (CalculatorException e) {
            throw new RuntimeException(e);
        }
        try {
            Assertions.assertThrows(CalculatorException.class,
                    () -> stackCalculator.calculates("(3,2) (2,3)"));
            Assertions.assertThrows(CalculatorException.class,
                    () -> stackCalculator.calculates("3,2)"));
            Assertions.assertThrows(CalculatorException.class,
                    () -> stackCalculator.calculates(""));
            Assertions.assertThrows(CalculatorException.class,
                    () -> stackCalculator.calculates("(3,2"));
            Assertions.assertThrows(CalculatorException.class,
                    () -> stackCalculator.calculates("(3,2,5)"));
            Assertions.assertThrows(CalculatorException.class,
                    () -> stackCalculator.calculates("(3.5.5,2)"));

            Assertions.assertThrows(CalculatorException.class,
                    () -> stackCalculator.calculates("+"));
            Assertions.assertThrows(CalculatorException.class,
                    () -> stackCalculator.calculates("aboba"));
            Assertions.assertThrows(CalculatorException.class,
                    () -> stackCalculator.calculates("+ 5;"));
            Assertions.assertEquals(stackCalculator.calculates("/ (4,3) (2,0) (2,1)"),
                    new ComplexNumber(2, 1.5));
        } catch (CalculatorException ignore) {
        }
        stackCalculator.toString();
        Assertions.assertThrows(NullPointerException.class,
                () -> stackCalculator.newLine(null));
        Assertions.assertEquals(new ComplexNumber(0, 0), stackCalculator.calculates("sin (0,0)"));
        Assertions.assertEquals(new ComplexNumber(1, 0), stackCalculator.calculates("cos (0,0)"));

    }

    /**
     * Just for percents.
     */
    @Test
    void testExceptions() {
        Assertions.assertThrows(CalculatorException.class,
                () -> {
                    throw new CalculatorException();
                });
        Assertions.assertThrows(CalculatorException.class,
                () -> {
                    throw new CalculatorException("hi");
                });
        Assertions.assertThrows(CalculatorException.class,
                () -> {
                    throw new NotEnoughArguments();
                });
        Assertions.assertThrows(CalculatorException.class,
                () -> {
                    throw new NotEnoughArguments("hi");
                });
        Assertions.assertThrows(CalculatorException.class,
                () -> {
                    throw new BadLexemeException();
                });
        Assertions.assertThrows(CalculatorException.class,
                () -> {
                    throw new BadLexemeException("hi");
                });
        Assertions.assertThrows(CalculatorException.class,
                () -> {
                    throw new NotNumberException();
                });
        Assertions.assertThrows(CalculatorException.class,
                () -> {
                    throw new NotNumberException("hi");
                });
        Assertions.assertThrows(CalculatorException.class,
                () -> {
                    throw new FunctionException();
                });
        Assertions.assertThrows(CalculatorException.class,
                () -> {
                    throw new FunctionException("hi");
                });

    }

    /**
     * Checks functions.
     *
     * @throws CalculatorException for fun
     */
    @Test
    void functionsTest() throws CalculatorException {
        ComplexFunctionWrapper complexFunctionWrapper = new ComplexFunctionWrapper(new Function<>() {
            @Override
            public int getArity() {
                return 2;
            }

            @Override
            public ComplexNumber apply(ComplexNumber[] arguments) {
                return Arrays.stream(arguments).max(Comparator.comparingDouble(ComplexNumber::module)).get();
            }

            @Override
            public String representation() {
                return null;
            }
        }, new StackCalculator());
        Assertions.assertEquals(2, complexFunctionWrapper.getArity());
        try {
            complexFunctionWrapper.addArg(new ComplexNumber(1, 0));
        } catch (CalculatorException ignore) {
        }
        Assertions.assertThrows(NotEnoughArguments.class,
                complexFunctionWrapper::apply);
        try {
            Assertions.assertThrows(NullPointerException.class,
                    () -> complexFunctionWrapper.addArg(null));
        } catch (NullPointerException e) {
            throw new RuntimeException(e);
        }

        var n = new ComplexNumber(1, 1);

        try {
            complexFunctionWrapper.addArg(n);
        } catch (CalculatorException e) {
            throw new RuntimeException(e);
        }
        complexFunctionWrapper.apply();
        Assertions.assertThrows(CalculatorException.class,
                () -> complexFunctionWrapper.addArg(n));
    }

    /**
     * Test parsers. Because some problems calculator prevents. So parser doesn't throw exception
     * in calculator.
     */
    @Test
    void testParsers() {
        ComplexNumberParser numberParser = new ComplexNumberParser();
        Assertions.assertThrows(CalculatorException.class, () -> numberParser.parseToken(""));
        Assertions.assertThrows(CalculatorException.class, () -> numberParser.parseToken("1.1.1"));
        Assertions.assertThrows(CalculatorException.class, () -> numberParser.parseToken("(1,1"));
        Assertions.assertThrows(CalculatorException.class, () -> numberParser.parseToken("1,1)"));
        Assertions.assertThrows(CalculatorException.class, () -> numberParser.parseToken("(1,1,1)"));
        Assertions.assertThrows(CalculatorException.class, () -> numberParser.parseToken("(1,1.1.1)"));
        Assertions.assertThrows(CalculatorException.class, () -> numberParser.parseToken(null));
        Assertions.assertFalse(numberParser.checkNumber(null));
        Assertions.assertFalse(numberParser.checkNumber(""));
    }

    /**
     * Tests adds.
     */
    @Test
    void testDynamic() {
        StackCalculator calculator = new StackCalculator();
        try {
            calculator.addToken("+");
            calculator.addToken("(1,2)");
            calculator.addToken("(3,2)");
            Assertions.assertEquals(new ComplexNumber(4, 4), calculator.calculates());
            var old = calculator.clear();
            calculator.newLine("- (4,4)");
            calculator.addToken(old.toString());
        } catch (CalculatorException e) {
            throw new RuntimeException(e);
        }

    }
}
