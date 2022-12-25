package ru.nsu.fit.tsukanov.calculator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.fit.tsukanov.calculator.core.Exceptions.BadLexemeException;
import ru.nsu.fit.tsukanov.calculator.core.Exceptions.CalculatorException;
import ru.nsu.fit.tsukanov.calculator.core.functions.Function;
import ru.nsu.fit.tsukanov.calculator.core.parser.CalculatorParser;
import ru.nsu.fit.tsukanov.calculator.core.parser.FunctionParserBuilder;
import ru.nsu.fit.tsukanov.calculator.core.parser.NumberParser;
import ru.nsu.fit.tsukanov.calculator.template.stack.StackCalculatorTemplate;

import java.util.List;

public class AnonCalculatorTest {
    @Test
    void basic() throws CalculatorException {
        StackCalculatorTemplate<Double> calculator = new StackCalculatorTemplate<>(
                new CalculatorParser<>(
                        new NumberParser<>() {
                            @Override
                            public Function<Double> parseToken(String token) throws BadLexemeException {
                                double val;
                                try {
                                    val = Double.parseDouble(token);
                                } catch (NumberFormatException e) {
                                    throw new BadLexemeException("Bad token: " + token);
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
                            public Double parseNumber(String token) throws BadLexemeException {

                                try {
                                    return Double.parseDouble(token);
                                } catch (NumberFormatException e) {
                                    throw new BadLexemeException("Bad token: " + token);
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

        Assertions.assertEquals(12 - Math.E, calculator.calculate("max; +; 5; 3; -; 12; e"));
    }
}
