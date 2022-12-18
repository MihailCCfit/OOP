package ru.nsu.fit.tsukanov.calculator.complex.parsers;

import ru.nsu.fit.tsukanov.calculator.complex.ComplexNumber;
import ru.nsu.fit.tsukanov.calculator.complex.Functions.ComplexAdd;
import ru.nsu.fit.tsukanov.calculator.complex.Functions.ComplexDiv;
import ru.nsu.fit.tsukanov.calculator.complex.Functions.ComplexMul;
import ru.nsu.fit.tsukanov.calculator.complex.Functions.ComplexSub;
import ru.nsu.fit.tsukanov.calculator.core.parser.BuildFunctionParser;
import ru.nsu.fit.tsukanov.calculator.core.parser.FunctionParser;

public class ComplexFunctionParser extends FunctionParser<ComplexNumber> {
    public static FunctionParser<ComplexNumber> getParser(){
        FunctionParser<ComplexNumber> functionParser =
                new BuildFunctionParser<ComplexNumber>()
                        .putFunction(new ComplexAdd())
                        .putFunction(new ComplexDiv())
                        .putFunction(new ComplexMul())
                        .putFunction(new ComplexSub())
                        .build();
        return functionParser;
    }
}
