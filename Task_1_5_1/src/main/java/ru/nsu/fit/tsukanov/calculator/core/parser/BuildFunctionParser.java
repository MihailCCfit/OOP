package ru.nsu.fit.tsukanov.calculator.core.parser;

import ru.nsu.fit.tsukanov.calculator.core.functions.Function;

import java.util.List;

public class BuildFunctionParser<T> {
    FunctionParser<T> functionParser;

    public BuildFunctionParser() {
        functionParser = new FunctionParser<>();
    }


    public BuildFunctionParser<T> putFunction(String representation, Function<T> function) {
        functionParser.putFunction(representation, function);
        return this;
    }

    public BuildFunctionParser<T> putFunction(Function<T> function) {
        functionParser.putFunction(function);
        return this;
    }
    public BuildFunctionParser<T> putFunction(List<Function<T>> functions){
        functions.forEach(this::putFunction);
        return this;
    }

    public FunctionParser<T> build() {
        return functionParser;
    }

}
