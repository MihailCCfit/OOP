package ru.nsu.fit.tsukanov.calculator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.fit.tsukanov.calculator.core.Calculator;
import ru.nsu.fit.tsukanov.calculator.core.Exceptions.BadTokenException;
import ru.nsu.fit.tsukanov.calculator.core.Exceptions.CalculatorException;
import ru.nsu.fit.tsukanov.calculator.core.functions.Function;
import ru.nsu.fit.tsukanov.calculator.core.parser.CalculatorParser;
import ru.nsu.fit.tsukanov.calculator.core.parser.functions.FunctionParserBuilder;
import ru.nsu.fit.tsukanov.calculator.core.parser.numbers.NumberParserInterface;
import ru.nsu.fit.tsukanov.calculator.template.stack.StackCalculatorTemplate;

import java.util.List;

public class AnonCalculatorTest {
    @Test
    void basic() throws CalculatorException {
        StackCalculatorTemplate<Double> calculator = new StackCalculatorTemplate<>(
                new CalculatorParser<>(
                        new NumberParserInterface<>() {
                            @Override
                            public Function<Double> parseToken(String token) throws BadTokenException {
                                double val;
                                try {
                                    val = Double.parseDouble(token);
                                } catch (NumberFormatException e) {
                                    throw new BadTokenException("Bad token: " + token);
                                }
                                return new Function<>() {
                                    @Override
                                    public int getArity() {
                                        return 0;
                                    }

                                    @Override
                                    public Double apply(List<Double> arguments) {
                                        return val;
                                    }

                                    @Override
                                    public String representation() {
                                        return Double.toString(val);
                                    }
                                };
                            }

                            @Override
                            public Double parseNumber(String token) throws BadTokenException {

                                try {
                                    return Double.parseDouble(token);
                                } catch (NumberFormatException e) {
                                    throw new BadTokenException("Bad token: " + token);
                                }
                            }

                            @Override
                            public boolean checkNumber(String token) {

                                try {
                                    Double.parseDouble(token);
                                } catch (NumberFormatException e) {
                                    return false;
                                }
                                return true;
                            }
                        },
                        new FunctionParserBuilder<Double>()
                                .putFunction("+", new Function<>() {
                                    /**
                                     * Return arity of function.
                                     *
                                     * @return arity of function
                                     */
                                    @Override
                                    public int getArity() {
                                        return 2;
                                    }

                                    /**
                                     * Apply function, depends on (or not) arguments.
                                     *
                                     * @param arguments arguments
                                     * @return number
                                     */
                                    @Override
                                    public Double apply(List<Double> arguments) {
                                        return arguments.get(0) + arguments.get(1);
                                    }

                                    /**
                                     * String representation.
                                     *
                                     * @return representation
                                     */
                                    @Override
                                    public String representation() {
                                        return "+";
                                    }
                                })
                                .putFunction(new Function<>() {
                                    @Override
                                    public int getArity() {
                                        return 2;
                                    }

                                    @Override
                                    public Double apply(List<Double> arguments) {
                                        return arguments.get(0) - arguments.get(1);
                                    }

                                    @Override
                                    public String representation() {
                                        return "-";
                                    }
                                })
                                .putFunction(new Function<>() {
                                    @Override
                                    public int getArity() {
                                        return 2;
                                    }

                                    @Override
                                    public Double apply(List<Double> arguments) {
                                        return Math.max(arguments.get(0), arguments.get(1));
                                    }

                                    @Override
                                    public String representation() {
                                        return "max";
                                    }
                                })
                                .putFunction(new Function<>() {
                                    @Override
                                    public int getArity() {
                                        return 0;
                                    }

                                    @Override
                                    public Double apply(List<Double> arguments) {
                                        return Math.E;
                                    }

                                    @Override
                                    public String representation() {
                                        return "e";
                                    }
                                })
                                .build()
                ),
                string -> string.split(";\\s*")
        );
        calculator.addToParser(new NumberParserInterface<>() {
            /**
             * Parse to Number.
             *
             * @param token token
             * @return number
             */
            @Override
            public Double parseNumber(String token) throws BadTokenException {
                if (!token.contains("/")) {
                    throw new BadTokenException("There is no /");
                }

                String[] strings = token.split("/");
                var numerator = Double.parseDouble(strings[0]);
                var denominator = Double.parseDouble(strings[1]);
                return numerator / denominator;
            }
        });
        Assertions.assertEquals(12 - Math.E, calculator.calculate("max; +; 5; 3; -; 12; e"));
        Assertions.assertEquals(5, calculator.calculate("10/2"));


        Calculator<Integer> calculator1 = new Calculator<>() {
            @Override
            public String newLine(String input) {
                return null;
            }

            @Override
            public Integer calculate() {
                return null;
            }

            @Override
            public Integer calculate(String line) {
                return null;
            }

            @Override
            public Integer getResult() {
                return null;
            }

            @Override
            public String getInformation() {
                return null;
            }
        };
        Assertions.assertThrows(UnsupportedOperationException.class, () -> calculator1.addToParser(token -> null));
        Assertions.assertThrows(UnsupportedOperationException.class, () -> calculator1.addToParser(new Function<>() {
            @Override
            public int getArity() {
                return 0;
            }

            @Override
            public Integer apply(List<Integer> arguments) {
                return null;
            }

            @Override
            public String representation() {
                return null;
            }
        }));
    }
}
