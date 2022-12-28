package ru.nsu.fit.tsukanov.calculator.real.parsers;

import ru.nsu.fit.tsukanov.calculator.core.parser.functions.FunctionParser;
import ru.nsu.fit.tsukanov.calculator.core.parser.functions.FunctionParserBuilder;
import ru.nsu.fit.tsukanov.calculator.real.functions.*;

import java.util.List;

public class RealFunctionParser extends FunctionParser<Double> {
    private RealFunctionParser(){}
    public static FunctionParser<Double> getParser() {
        return new FunctionParserBuilder<Double>()
                .putFunction(List.of(new RealAdd(), new RealDiv()))
                .putFunction(new RealMul())
                .putFunction("-", new RealSub())
                .putFunction(new RealSub())
                .putFunction(new RealLog())
                .putFunction(new RealSin())
                .putFunction(new RealCos())
                .putFunction(List.of(new RealTan(), new RealSqrt(), new RealPower()))
                .build();
    }
}
