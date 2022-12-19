package ru.nsu.fit.tsukanov.calculator.core.parser;

/**
 * Tokenize string.
 */
public interface Lexer {
    /**
     * Get tokens.
     *
     * @param string the string that will be seperated by tokens
     * @return array of tokens.
     */
    String[] getTokens(String string);
}
