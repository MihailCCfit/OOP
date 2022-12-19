package ru.nsu.fit.tsukanov.calculator.core.parser;

import ru.nsu.fit.tsukanov.calculator.core.functions.Function;

import java.util.List;

/**
 * Useful and comfortable builder for functionParser.
 * @param <T> the number for calculator (function return type)
 */
public class BuildFunctionParser<T> {
    private final FunctionParser<T> functionParser;

    /**
     * Start build.
     */
    public BuildFunctionParser() {
        functionParser = new FunctionParser<>();
    }

    /**
     * Put function into the parser for recognize that.
     *
     * @param representation string by which function will be recognized
     * @param function the function that will be saved in the parser
     * @return this for chaining methods
     */
    public BuildFunctionParser<T> putFunction(String representation, Function<T> function) {
        functionParser.putFunction(representation, function);
        return this;
    }

    /**
     * Put function into the parser for recognize that.
     *
     * @param function the function that will be saved in the parser
     * @return this builder
     */
    public BuildFunctionParser<T> putFunction(Function<T> function) {
        functionParser.putFunction(function);
        return this;
    }

    /**
     * Put functions into the parser for recognize them.
     *
     * @param functions the list of functions
     * @return this builder
     */
    public BuildFunctionParser<T> putFunction(List<Function<T>> functions){
        functions.forEach(this::putFunction);
        return this;
    }

    /**
     * Return built (created) function parser.
     * @return built parser
     */

    public FunctionParser<T> build() {
        return functionParser;
    }

}
