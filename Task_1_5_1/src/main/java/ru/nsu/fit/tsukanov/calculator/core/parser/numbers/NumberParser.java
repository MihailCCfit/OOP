package ru.nsu.fit.tsukanov.calculator.core.parser.numbers;

import ru.nsu.fit.tsukanov.calculator.core.Exceptions.BadLexemeException;
import ru.nsu.fit.tsukanov.calculator.core.functions.Function;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NumberParser<T> implements NumberParserInterface<T> {
    private final Set<NumberParserInterface<T>> numberParsers = new HashSet<>();

    public boolean putNumberParser(NumberParserInterface<T> numberParser) {
        return numberParsers.add(numberParser);
    }

    private List<T> listOfParsed(String token) throws BadLexemeException {
        List<T> results = new ArrayList<>();
        for (NumberParserInterface<T> numberParser : numberParsers) {
            if (numberParser.checkNumber(token)) {
                results.add(numberParser.parseNumber(token));
            }
        }
        return results;
    }

    /**
     * Parse token.
     *
     * @param token token
     * @return constant function
     */
    @Override
    public Function<T> parseToken(String token) throws BadLexemeException {
        List<T> list;
        try {
            list = listOfParsed(token);
        } catch (BadLexemeException e) {
            throw new RuntimeException(e);
        }
        if (list.size() != 1) {
            throw new BadLexemeException("There is no valid possible number: " + list);
        }
        var num = list.get(0);
        return new Function<>() {
            @Override
            public int getArity() {
                return 0;
            }

            @Override
            public T apply(List<T> arguments) {
                return num;
            }

            @Override
            public String representation() {
                return num.toString();
            }
        };
    }

    /**
     * Parse to Number.
     *
     * @param token token
     * @return number
     */
    @Override
    public T parseNumber(String token) throws BadLexemeException {
        return parseToken(token).apply(List.of());
    }

    /**
     * Check for number.
     *
     * @param token token
     * @return true if token is number.
     */
    @Override
    public boolean checkNumber(String token) {
        try {
            return listOfParsed(token).size() == 1;
        } catch (BadLexemeException e) {
            return false;
        }
    }
}
