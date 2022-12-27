package ru.nsu.fit.tsukanov.calculator.template.example;

import ru.nsu.fit.tsukanov.calculator.complex.ComplexNumber;
import ru.nsu.fit.tsukanov.calculator.complex.parsers.ComplexFunctionParser;
import ru.nsu.fit.tsukanov.calculator.complex.parsers.ComplexNumberParser;
import ru.nsu.fit.tsukanov.calculator.complex.parsers.RealNumberParser;
import ru.nsu.fit.tsukanov.calculator.core.parser.CalculatorParser;
import ru.nsu.fit.tsukanov.calculator.core.parser.Lexer;
import ru.nsu.fit.tsukanov.calculator.core.parser.numbers.NumberParserBuilder;
import ru.nsu.fit.tsukanov.calculator.template.stack.StackCalculatorTemplate;

/**
 * Prefix calculator for complex numbers.
 */
public class StackCalculatorInstance extends StackCalculatorTemplate<ComplexNumber> {
    public StackCalculatorInstance(CalculatorParser<ComplexNumber> calculatorParser, Lexer lexer) {
        super(calculatorParser, lexer);
    }

    public StackCalculatorInstance() {
        this(new CalculatorParser<>(new NumberParserBuilder<ComplexNumber>()
                        .putParser(new ComplexNumberParser())
                        .putParser(new RealNumberParser())
                        .build(), ComplexFunctionParser.getParser()),
                string -> string.split("\\s+"));
    }

}
