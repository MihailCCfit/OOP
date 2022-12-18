package ru.nsu.fit.tsukanov.calculator.core.parser;

import ru.nsu.fit.tsukanov.calculator.core.Exceptions.BadLexemeException;
import ru.nsu.fit.tsukanov.calculator.core.functions.Function;

import java.util.HashMap;
import java.util.Map;

public class FunctionParser<T> {
    private final Map<String, Function<T>> dictionary = new HashMap<>();


    public Function<T> putFunction(Function<T> function) {
        return putFunction(function.representation(), function);
    }

    public Function<T> putFunction(String representation, Function<T> function) {
        return dictionary.put(representation, function);
    }

    public boolean checkToken(String token){
        return dictionary.containsKey(token);
    }
    public Function<T> parseToken(String token) throws BadLexemeException {
        if (!checkToken(token)){
            throw new BadLexemeException();
        }
        return dictionary.get(token);
    }
}
