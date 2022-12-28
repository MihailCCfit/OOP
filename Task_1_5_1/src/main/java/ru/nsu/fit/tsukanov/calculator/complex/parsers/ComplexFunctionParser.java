package ru.nsu.fit.tsukanov.calculator.complex.parsers;

import ru.nsu.fit.tsukanov.calculator.complex.ComplexNumber;
import ru.nsu.fit.tsukanov.calculator.complex.functions.*;
import ru.nsu.fit.tsukanov.calculator.core.parser.functions.FunctionParser;
import ru.nsu.fit.tsukanov.calculator.core.parser.functions.FunctionParserBuilder;

import java.util.List;

public class ComplexFunctionParser extends FunctionParser<ComplexNumber> {
    private ComplexFunctionParser(){}
    public static FunctionParser<ComplexNumber> getParser() {

        return new FunctionParserBuilder<ComplexNumber>()
                .putFunction(List.of(new ComplexAdd(), new ComplexDiv()))
                .putFunction(new ComplexMul())
                .putFunction("-", new ComplexSub())
                .putFunction(new ComplexLog())
                .putFunction(new ComplexSin())
                .putFunction(new ComplexCos())
                .build();
    }
}
