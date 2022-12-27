package ru.nsu.fit.tsukanov.calculator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.fit.tsukanov.calculator.coding.CodingCalculator;
import ru.nsu.fit.tsukanov.calculator.coding.methods.CaesarCoding;
import ru.nsu.fit.tsukanov.calculator.coding.methods.CaesarDeCoding;
import ru.nsu.fit.tsukanov.calculator.core.Exceptions.BadLexemeException;
import ru.nsu.fit.tsukanov.calculator.core.Exceptions.CalculatorException;
import ru.nsu.fit.tsukanov.calculator.core.parser.CalculatorParser;
import ru.nsu.fit.tsukanov.calculator.core.parser.functions.FunctionParser;
import ru.nsu.fit.tsukanov.calculator.core.parser.functions.FunctionParserBuilder;
import ru.nsu.fit.tsukanov.calculator.core.parser.numbers.NumberParser;
import ru.nsu.fit.tsukanov.calculator.core.parser.numbers.NumberParserInterface;

public class CaesarCodingTest {
    @Test
    void testCoding() {
        FunctionParser<String> functionParser = new FunctionParserBuilder<String>()
                .putFunction(new CaesarCoding())
                .putFunction(new CaesarDeCoding())
                .build();
        NumberParser<String> numberParser = new NumberParser<>();
        numberParser.putNumberParser(new NumberParserInterface<>() {
            /**
             * Parse to Number.
             *
             * @param token token
             * @return number
             */
            @Override
            public String parseNumber(String token) throws BadLexemeException {
                for (char c : token.toCharArray()) {
                    if (!Character.isAlphabetic(c)) {
                        throw new BadLexemeException(c + " is not alphabetic");
                    }
                }
                return token;
            }
        });
        numberParser.putNumberParser(new NumberParserInterface<>() {
            /**
             * Parse to Number.
             *
             * @param token token
             * @return number
             */
            @Override
            public String parseNumber(String token) throws BadLexemeException {
                for (char c : token.toCharArray()) {
                    if (!Character.isDigit(c)) {
                        throw new BadLexemeException(c + " is not alphabetic");
                    }
                }
                return token;
            }
        });
        CalculatorParser<String> calculatorParser = new CalculatorParser<>(numberParser, functionParser);
        CodingCalculator codingCalculator = new CodingCalculator(calculatorParser);
        String str = "abab";
        try {
            Assertions.assertEquals(str, codingCalculator.calculate(String.format(
                    "%s 3 %s 3 %s",
                    new CaesarCoding().representation(),
                    new CaesarDeCoding().representation(),
                    str
            )));
            Assertions.assertEquals("bcbc", codingCalculator.calculate(String.format(
                    "%s 3 %s 2 %s",
                    new CaesarCoding().representation(),
                    new CaesarDeCoding().representation(),
                    str
            )));
        } catch (CalculatorException e) {
            throw new RuntimeException(e);
        }
    }
}
