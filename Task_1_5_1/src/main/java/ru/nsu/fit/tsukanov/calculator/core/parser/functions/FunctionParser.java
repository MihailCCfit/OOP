package ru.nsu.fit.tsukanov.calculator.core.parser.functions;

import ru.nsu.fit.tsukanov.calculator.core.Exceptions.BadTokenException;
import ru.nsu.fit.tsukanov.calculator.core.functions.Function;

import java.util.HashMap;
import java.util.Map;

/**
 * Function parser. Parses by the map of name function to the function.
 *
 * @param <T> the number
 */
public class FunctionParser<T> {
    private final Map<String, Function<T>> dictionary = new HashMap<>();

    /**
     * Put new function for identification.
     *
     * @param function new function that will be added to the parser
     * @return previous version
     */
    public Function<T> putFunction(Function<T> function) {
        return putFunction(function.representation(), function);
    }

    /**
     * Put new function for identification.
     *
     * @param representation the string representation by that function will be recognized.
     * @param function       new function that will be added to the parser
     * @return previous version
     */
    public Function<T> putFunction(String representation, Function<T> function) {
        return dictionary.put(representation, function);
    }

    /**
     * Check token for the validity.
     *
     * @param token the string that will be checked for function.
     * @return true if token can be recognized
     */

    public boolean checkToken(String token) {
        return dictionary.containsKey(token);
    }

    /**
     * Parse token to the function according to the saved function in the map.
     *
     * @param token the string(token) for parsing
     * @return function according to the parser and token
     * @throws BadTokenException if there is problem with parsing
     */
    public Function<T> parseToken(String token) throws BadTokenException {
        if (!checkToken(token)) {
            throw new BadTokenException();
        }
        return dictionary.get(token);
    }
}
