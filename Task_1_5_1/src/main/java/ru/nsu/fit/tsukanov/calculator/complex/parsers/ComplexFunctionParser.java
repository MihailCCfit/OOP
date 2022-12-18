package ru.nsu.fit.tsukanov.calculator.complex.parsers;

import ru.nsu.fit.tsukanov.calculator.complex.ComplexNumber;
import ru.nsu.fit.tsukanov.calculator.complex.Functions.ComplexAdd;
import ru.nsu.fit.tsukanov.calculator.complex.Functions.ComplexDiv;
import ru.nsu.fit.tsukanov.calculator.complex.Functions.ComplexMul;
import ru.nsu.fit.tsukanov.calculator.complex.Functions.ComplexSub;
import ru.nsu.fit.tsukanov.calculator.core.functions.Function;
import ru.nsu.fit.tsukanov.calculator.core.parser.BuildFunctionParser;
import ru.nsu.fit.tsukanov.calculator.core.parser.FunctionParser;

import java.util.List;

public class ComplexFunctionParser extends FunctionParser<ComplexNumber> {
    public static FunctionParser<ComplexNumber> getParser() {
        return new BuildFunctionParser<ComplexNumber>()
                .putFunction(List.of(new ComplexAdd(), new ComplexDiv()))
                .putFunction(new ComplexMul())
                .putFunction("-", new ComplexSub())
                .putFunction("sin", new Function<>() {
                    @Override
                    public int getArity() {
                        return 1;
                    }

                    @Override
                    public ComplexNumber apply(ComplexNumber[] arguments) {
                        ComplexNumber num = arguments[0];
                        double real = num.getReal();
                        double im = num.getImaginary();
                        return new ComplexNumber(Math.sin(real) * Math.cosh(im),
                                Math.cos(real) * Math.sinh(im));
                    }

                    @Override
                    public String representation() {
                        return "sin";
                    }
                })
                .putFunction("cos", new Function<>() {
                    @Override
                    public int getArity() {
                        return 1;
                    }

                    @Override
                    public ComplexNumber apply(ComplexNumber[] arguments) {
                        ComplexNumber num = arguments[0];
                        double real = num.getReal();
                        double im = num.getImaginary();
                        return new ComplexNumber(Math.cos(real) * Math.cosh(im),
                                -1 * Math.sin(real) * Math.sinh(im));
                    }

                    @Override
                    public String representation() {
                        return null;
                    }
                })
                .build();
    }
}
