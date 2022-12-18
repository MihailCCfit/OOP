package ru.nsu.fit.tsukanov.calculator.core.parser;

import ru.nsu.fit.tsukanov.calculator.core.Exceptions.BadLexemeException;
import ru.nsu.fit.tsukanov.calculator.core.functions.Function;

public class CalculatorParser<T> {
    protected NumberParser<T> numberParser;
    protected FunctionParser<T> functionParser;

    public CalculatorParser(NumberParser<T> numberParser, FunctionParser<T> functionParser) {
        this.numberParser = numberParser;
        this.functionParser = functionParser;
    }

    public boolean isNumber(String token) {
        return numberParser.checkNumber(token);
    }

    public boolean isFunction(String token) {
        return functionParser.checkToken(token);
    }

    public Function<T> parse(String token) throws BadLexemeException {
        if (isFunction(token)) {
            return functionParser.parseToken(token);
        }
        if (isNumber(token)) {
            return numberParser.parseToken(token);
        }
        throw new BadLexemeException("{" + token + "} not recognized");
    }
}
