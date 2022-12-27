package ru.nsu.fit.tsukanov.calculator.coding.methods;

import ru.nsu.fit.tsukanov.calculator.core.functions.Function;

import java.util.List;

public class CaesarDeCoding implements Function<String> {
    /**
     * Return arity of function.
     *
     * @return arity of function
     */
    @Override
    public int getArity() {
        return 2;
    }

    /**
     * Apply function, depends on (or not) arguments.
     *
     * @param arguments arguments
     * @return number
     */
    @Override
    public String apply(List<String> arguments) {
        int shift = Integer.parseInt(arguments.get(0));
        String message = arguments.get(1);
        StringBuilder result = new StringBuilder();
        for (char character : message.toCharArray()) {
            if (character != ' ') {
                int originalAlphabetPosition = character - 'a';
                int newAlphabetPosition = (originalAlphabetPosition - shift) % 26;
                if (newAlphabetPosition<0){
                    newAlphabetPosition+=26;
                }
                char newCharacter = (char) ('a' + newAlphabetPosition);
                result.append(newCharacter);
            } else {
                result.append(character);
            }
        }
        return result.toString();
    }

    /**
     * String representation.
     *
     * @return representation
     */
    @Override
    public String representation() {
        return "caesarDeCode";
    }
}
