package ru.nsu.fit.tsukanov.calculator.core.parser.numbers;

import java.util.List;

public class NumberParserBuilder<T> {
    private final NumberParser<T> numberParser;

    /**
     * Start build.
     */
    public NumberParserBuilder() {
        numberParser = new NumberParser<>();
    }

    /**
     * Put function into the parser for recognize that.
     *
     * @param parser the function that will be saved in the parser
     * @return this for chaining methods
     */
    public NumberParserBuilder<T> putParser(NumberParserInterface<T> parser) {
        numberParser.putNumberParser(parser);
        return this;
    }

    /**
     * Put functions into the parser for recognize them.
     *
     * @param functions the list of functions
     * @return this builder
     */
    public NumberParserBuilder<T> putParser(List<NumberParserInterface<T>> functions) {
        functions.forEach(this::putParser);
        return this;
    }

    /**
     * Return built (created) function parser.
     *
     * @return built parser
     */

    public NumberParser<T> build() {
        return numberParser;
    }
}
