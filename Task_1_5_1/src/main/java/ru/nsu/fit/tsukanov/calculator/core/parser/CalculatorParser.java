package ru.nsu.fit.tsukanov.calculator.core.parser;

import ru.nsu.fit.tsukanov.calculator.core.Exceptions.BadTokenException;
import ru.nsu.fit.tsukanov.calculator.core.functions.Function;
import ru.nsu.fit.tsukanov.calculator.core.parser.functions.FunctionParser;
import ru.nsu.fit.tsukanov.calculator.core.parser.numbers.NumberParser;
import ru.nsu.fit.tsukanov.calculator.core.parser.numbers.NumberParserInterface;

/**
 * Combines function and number parsers. Generate function from string (token).
 *
 * @param <T> the number
 * @see FunctionParser
 * @see NumberParserInterface
 * @see Function
 */
public class CalculatorParser<T> {
    /**
     * Number Parser
     */
    protected NumberParser<T> numberParser;
    /**
     * Function parser
     */
    protected FunctionParser<T> functionParser;

    /**
     * Creates parser by the specified number and function parsers
     *
     * @param numberParser   the number parser
     * @param functionParser the function parser
     */
    public CalculatorParser(NumberParserInterface<T> numberParser, FunctionParser<T> functionParser) {
        this.numberParser = new NumberParser<>();
        this.numberParser.putNumberParser(numberParser);
        this.functionParser = functionParser;
    }

    /**
     * Check token for number according to the number parser.
     *
     * @param token that will be checked for number
     * @return true if token can be recognized as number
     */
    public boolean isNumber(String token) {
        return numberParser.checkNumber(token);
    }

    /**
     * Check token for function according to the function parser.
     *
     * @param token that will be checked for function
     * @return true if token can be recognized as function
     */
    public boolean isFunction(String token) {
        return functionParser.checkToken(token);
    }

    /**
     * Parse token as number or as function (checked for function firstly).
     * If there is some problem then throws exception.
     * Return function (function can be zero-arity that means number or the constant).
     *
     * @param token token that parser try to recognize
     * @return the function of the parsed token
     * @throws BadTokenException if there is problem with parsing.
     */
    public Function<T> parse(String token) throws BadTokenException {
        if (isFunction(token)) {
            return functionParser.parseToken(token);
        }
        if (isNumber(token)) {
            return numberParser.parseToken(token);
        }
        throw new BadTokenException("{" + token + "} not recognized");
    }

    public boolean addParser(NumberParserInterface<T> numberParser) {
        return this.numberParser.putNumberParser(numberParser);
    }
    public boolean addFunction(Function<T> function){
        return this.functionParser.putFunction(function)==null;
    }
}
