package ru.nsu.fit.tsukanov.calculator.core.parser.functions;

import ru.nsu.fit.tsukanov.calculator.core.functions.Function;

import java.util.List;

/**
 * Useful and comfortable builder for functionParser.
 *
 * @param <T> the number for calculator (function return type)
 */
public class FunctionParserBuilder<T> {
    private final FunctionParser<T> functionParser;

    /**
     * Start build.
     */
    public FunctionParserBuilder() {
        functionParser = new FunctionParser<>();
    }

    /**
     * Put function into the parser for recognize that.
     *
     * @param representation string by which function will be recognized
     * @param function       the function that will be saved in the parser
     * @return this for chaining methods
     */
    public FunctionParserBuilder<T> putFunction(String representation, Function<T> function) {
        functionParser.putFunction(representation, function);
        return this;
    }

    /**
     * Put function into the parser for recognize that.
     *
     * @param function the function that will be saved in the parser
     * @return this builder
     */
    public FunctionParserBuilder<T> putFunction(Function<T> function) {
        functionParser.putFunction(function);
        return this;
    }

    /**
     * Put functions into the parser for recognize them.
     *
     * @param functions the list of functions
     * @return this builder
     */
    public FunctionParserBuilder<T> putFunction(List<Function<T>> functions) {
        for (Function<T> function : functions) {
            putFunction(function);
        }
        return this;
    }

    /**
     * Return built (created) function parser.
     *
     * @return built parser
     */

    public FunctionParser<T> build() {
        return functionParser;
    }

}
