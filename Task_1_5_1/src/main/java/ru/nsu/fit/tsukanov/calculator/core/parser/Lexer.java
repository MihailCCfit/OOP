package ru.nsu.fit.tsukanov.calculator.core.parser;

public interface Lexer {
    /**
     * Get tokens.
     * @param string the string that will be seperated by tokens
     * @return array of tokens.
     */
    String[] getTokens(String string);
}
