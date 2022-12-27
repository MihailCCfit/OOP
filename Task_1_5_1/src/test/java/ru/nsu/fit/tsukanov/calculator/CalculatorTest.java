package ru.nsu.fit.tsukanov.calculator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.nsu.fit.tsukanov.calculator.complex.ComplexFunctionWrapper;
import ru.nsu.fit.tsukanov.calculator.complex.ComplexNumber;
import ru.nsu.fit.tsukanov.calculator.complex.Functions.ComplexSub;
import ru.nsu.fit.tsukanov.calculator.complex.StackCalculator;
import ru.nsu.fit.tsukanov.calculator.complex.parsers.ComplexNumberParser;
import ru.nsu.fit.tsukanov.calculator.complex.parsers.RealNumberParser;
import ru.nsu.fit.tsukanov.calculator.complex.recursive.RecursiveCalculator;
import ru.nsu.fit.tsukanov.calculator.core.Calculator;
import ru.nsu.fit.tsukanov.calculator.core.Exceptions.*;
import ru.nsu.fit.tsukanov.calculator.core.functions.Function;
import ru.nsu.fit.tsukanov.calculator.core.parser.numbers.NumberParser;
import ru.nsu.fit.tsukanov.calculator.core.parser.numbers.NumberParserBuilder;
import ru.nsu.fit.tsukanov.calculator.core.parser.numbers.NumberParserInterface;
import ru.nsu.fit.tsukanov.calculator.template.example.StackCalculatorInstance;
import ru.nsu.fit.tsukanov.calculator.template.stack.FunctionWrapperStack;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class CalculatorTest {


    public static Stream<Calculator<ComplexNumber>> calculatorStream() {
        return Stream.of(new StackCalculatorInstance(), new StackCalculator(), new RecursiveCalculator());
    }


    /**
     * Test calculator calculating with valid and invalid input.
     *
     * @throws CalculatorException if there is problem (can't be)
     */
    @ParameterizedTest
    @MethodSource("calculatorStream")
    void test(Calculator<ComplexNumber> calculator) throws CalculatorException {

        calculator.newLine("+ (1,2) (2,5)");
        try {
            Assertions.assertEquals(calculator.calculate(), new ComplexNumber(3, 7));
            System.out.println(calculator.getInformation());
            Assertions.assertEquals(calculator.getResult(), new ComplexNumber(3, 7));
            Assertions.assertEquals(calculator.calculate("- (3,2) (2,3)"),
                    new ComplexNumber(1, -1));
            Assertions.assertEquals(calculator.calculate("* (2,0) (2,3)"),
                    new ComplexNumber(4, 6));
            Assertions.assertEquals(calculator.calculate("/ (4,3) (2,0)"),
                    new ComplexNumber(2, 1.5));
            Assertions.assertEquals(calculator.calculate("ln (1,0)"),
                    new ComplexNumber(0, 0));
            Assertions.assertEquals(calculator.calculate("ln (0,1)"),
                    new ComplexNumber(0, Math.PI / 2));
            Assertions.assertEquals(calculator.calculate("ln (0,-1)"),
                    new ComplexNumber(0, -Math.PI / 2));
            Assertions.assertEquals(calculator.calculate("ln (1,1)"),
                    new ComplexNumber(Math.log(Math.sqrt(2)), Math.PI / 4));
            Assertions.assertEquals(calculator.calculate("ln (-1,-1)"),
                    new ComplexNumber(Math.log(Math.sqrt(2)), -3 * Math.PI / 4));
            Assertions.assertEquals(calculator.calculate("ln (-1,0)"),
                    new ComplexNumber(0, Math.PI));
        } catch (CalculatorException e) {
            throw new RuntimeException(e);
        }
        try {
            Assertions.assertThrows(CalculatorException.class,
                    () -> calculator.calculate("(3,2) (2,3)"));
            Assertions.assertThrows(CalculatorException.class,
                    () -> calculator.calculate("3,2)"));
            Assertions.assertThrows(CalculatorException.class,
                    () -> calculator.calculate(""));
            Assertions.assertThrows(CalculatorException.class,
                    () -> calculator.calculate("(3,2"));
            Assertions.assertThrows(CalculatorException.class,
                    () -> calculator.calculate("(3,2,5)"));
            Assertions.assertThrows(CalculatorException.class,
                    () -> calculator.calculate("(3.5.5,2)"));

            Assertions.assertThrows(CalculatorException.class,
                    () -> calculator.calculate("+"));
            Assertions.assertThrows(CalculatorException.class,
                    () -> calculator.calculate("aboba"));
            Assertions.assertThrows(CalculatorException.class,
                    () -> calculator.calculate("+ 5;"));
            Assertions.assertEquals(calculator.calculate("/ (4,3) (2,0) (2,1)"),
                    new ComplexNumber(2, 1.5));
        } catch (CalculatorException ignore) {
        }
        calculator.toString();
        Assertions.assertThrows(NullPointerException.class,
                () -> calculator.newLine(null));
        Assertions.assertEquals(new ComplexNumber(0, 0), calculator.calculate("sin  (0,0)"));
        Assertions.assertEquals(new ComplexNumber(1, 0), calculator.calculate("cos (0,0)"));
        calculator.addToParser(new NumberParserInterface<>() {
            private static double fib(int index) {
                double x1 = 0;
                double x2 = 1;
                for (int i = 0; i < index; i++) {
                    x2 = x2 + x1;
                    x1 = x2 - x1;
                }
                return x2;
            }

            @Override
            public ComplexNumber parseNumber(String token) throws BadLexemeException {
                String fib = "fib";
                if (token.startsWith(fib)) {
                    token = token.substring(fib.length());
                    if (token.charAt(0) == '(' && token.charAt(token.length() - 1) == ')') {
                        token = token.substring(1, token.length() - 1);
                        try {
                            return new ComplexNumber(fib(Integer.parseInt(token)), 0);
                        } catch (NumberFormatException e) {
                            throw new BadLexemeException(e.getMessage());
                        }
                    }
                }
                throw new BadLexemeException("Bad token: " + token);
            }
        });
        calculator.addToParser(new Function<>() {
            @Override
            public int getArity() {
                return 1;
            }

            @Override
            public ComplexNumber apply(List<ComplexNumber> arguments) {
                var number = arguments.get(0);
                return new ComplexNumber(number.imaginary(), number.real());
            }

            @Override
            public String representation() {
                return "swap";
            }
        });
        Assertions.assertEquals(new ComplexNumber(0, 2), calculator.calculate("swap - fib(4) fib(3)"));
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
            public ComplexNumber apply(List<ComplexNumber> arguments) {
                return arguments.stream().max(Comparator.comparingDouble(ComplexNumber::module)).get();
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
        Assertions.assertEquals("-", new ComplexSub().representation());

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
            Assertions.assertEquals(new ComplexNumber(4, 4), calculator.calculate());
            var old = calculator.clear();
            calculator.newLine("- (4,4)");
            calculator.getInformation();
            calculator.addToken(old.toString());
        } catch (CalculatorException e) {
            throw new RuntimeException(e);
        }

    }

    @ParameterizedTest
    @MethodSource("calculatorStream")
    void testRecursiveCalculator(Calculator<ComplexNumber> calculator) throws CalculatorException {
        System.out.println(calculator.calculate("(1,2)"));
        Assertions.assertEquals(new ComplexNumber(1, 2),
                calculator.calculate("(1,2)"));
        Assertions.assertEquals(calculator.calculate("- (3,2) (2,3)"),
                new ComplexNumber(1, -1));
        Assertions.assertEquals(calculator.calculate("* (2,0) (2,3)"),
                new ComplexNumber(4, 6));
        Assertions.assertEquals(calculator.calculate("/ (4,3) (2,0)"),
                new ComplexNumber(2, 1.5));
        Assertions.assertEquals(calculator.calculate("ln 1"),
                new ComplexNumber(0, 0));
        Assertions.assertEquals(calculator.calculate("ln (0,1)"),
                new ComplexNumber(0, Math.PI / 2));
        Assertions.assertEquals(calculator.calculate("ln (0,-1)"),
                new ComplexNumber(0, -Math.PI / 2));
        Assertions.assertEquals(calculator.calculate("ln (1,1)"),
                new ComplexNumber(Math.log(Math.sqrt(2)), Math.PI / 4));
        Assertions.assertEquals(calculator.calculate("ln (-1,-1)"),
                new ComplexNumber(Math.log(Math.sqrt(2)), -3 * Math.PI / 4));
        Assertions.assertEquals(calculator.calculate("ln -1"),
                new ComplexNumber(0, Math.PI));
        calculator.newLine("+ + (0,0) (0,0) (0,0)");
        Assertions.assertEquals(calculator.calculate(), new ComplexNumber(0, 0));

        calculator.toString();
        Assertions.assertThrows(NullPointerException.class,
                () -> calculator.newLine(null));
        Assertions.assertEquals(new ComplexNumber(0, 0), calculator.calculate("sin (0,0)"));
        Assertions.assertEquals(new ComplexNumber(1, 0), calculator.calculate("sin (90`,0`)"));
        Assertions.assertEquals(new ComplexNumber(1, 0), calculator.calculate("cos (0,0)"));
        try {
            Assertions.assertThrows(CalculatorException.class,
                    () -> calculator.calculate("(3,2) (2,3)"));
            Assertions.assertThrows(CalculatorException.class,
                    () -> calculator.calculate("3,2)"));
            Assertions.assertThrows(CalculatorException.class,
                    () -> calculator.calculate(""));
            Assertions.assertThrows(CalculatorException.class,
                    () -> calculator.calculate("(3,2"));
            Assertions.assertThrows(CalculatorException.class,
                    () -> calculator.calculate("(3,2,5)"));
            Assertions.assertThrows(CalculatorException.class,
                    () -> calculator.calculate("(3.5.5,2)"));

            Assertions.assertThrows(CalculatorException.class,
                    () -> calculator.calculate("+"));
            Assertions.assertThrows(CalculatorException.class,
                    () -> calculator.calculate("aboba"));
            Assertions.assertThrows(CalculatorException.class,
                    () -> calculator.calculate("+ 5;"));
            Assertions.assertEquals(calculator.calculate("/ (4,3) (2,0) (2,1)"),
                    new ComplexNumber(2, 1.5));
            Assertions.assertEquals(calculator.calculate("+ 1 2"),
                    new ComplexNumber(3, 0));
        } catch (CalculatorException ignore) {
        }
    }

    @Test
    void testOthers() {
        NumberParserInterface<Integer> numberParser = token -> {
            try {
                return Integer.parseInt(token);
            } catch (NumberFormatException e) {
                throw new BadLexemeException(e.getMessage());
            }

        };
        NumberParserInterface<Integer> numberParser2 = token -> {
            try {
                return Integer.parseInt(token);
            } catch (NumberFormatException e) {
                throw new BadLexemeException(e.getMessage());
            }

        };
        try {
            Function<Integer> fun = numberParser.parseToken("5");
            Assertions.assertEquals(5, fun.apply(List.of()));
            Assertions.assertEquals(0, fun.getArity());
            Assertions.assertFalse(fun.representation().isEmpty());
            Assertions.assertThrows(BadLexemeException.class,
                    () -> numberParser.parseToken(".."));
        } catch (BadLexemeException e) {
            throw new RuntimeException(e);
        }
        NumberParser<Integer> numberParser1 = new NumberParser<>();
        numberParser1.putNumberParser(numberParser);
        try {
            Assertions.assertEquals("1", numberParser1.parseToken("1").representation());
        } catch (BadLexemeException e) {
            throw new RuntimeException(e);
        }
        numberParser1.putNumberParser(numberParser2);
        NumberParser<Integer> numberParser11 = new NumberParserBuilder<Integer>()
                .putParser(List.of(numberParser1))
                .build();
        Assertions.assertThrows(BadLexemeException.class, () -> numberParser11.parseNumber("1"));
        Assertions.assertThrows(BadLexemeException.class, () -> numberParser11.parseNumber("."));
        FunctionWrapperStack<Integer> functionWrapper = new FunctionWrapperStack<>(
                new Function<>() {
                    @Override
                    public int getArity() {
                        return 1;
                    }

                    @Override
                    public Integer apply(List<Integer> arguments) {
                        return arguments.get(0) * 3 - 1;
                    }

                    @Override
                    public String representation() {
                        return "1";
                    }
                }
                , null);
        Assertions.assertThrows(CalculatorException.class, () -> functionWrapper.addArg(null));
        Assertions.assertThrows(CalculatorException.class, () -> {
            functionWrapper.addArg(0);
            functionWrapper.addArg(0);
        });
        RealNumberParser realNumberParser = new RealNumberParser();
        Function<ComplexNumber> fun;
        try {
            fun = realNumberParser.parseToken("5.5");
        } catch (BadLexemeException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(new ComplexNumber(5.5, 0), fun.apply(List.of()));
        Assertions.assertEquals(0, fun.getArity());
        Assertions.assertEquals("5.5", fun.representation());
        ComplexNumberParser complexNumberParser = new ComplexNumberParser();
        try {
            fun = complexNumberParser.parseToken("(5,10)");
        } catch (BadLexemeException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(0, fun.getArity());
        Assertions.assertEquals(new ComplexNumber(5, 10).toString(), fun.representation());
    }

}
