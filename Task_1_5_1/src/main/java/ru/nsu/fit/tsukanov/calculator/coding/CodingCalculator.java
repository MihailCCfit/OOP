package ru.nsu.fit.tsukanov.calculator.coding;

import ru.nsu.fit.tsukanov.calculator.core.parser.CalculatorParser;
import ru.nsu.fit.tsukanov.calculator.template.stack.StackCalculatorTemplate;

public class CodingCalculator extends StackCalculatorTemplate<String> {


    /**
     * Creates stackCalculator, that has stack of functionWrapper. functionWrapper saves arguments
     * while it isn't available.
     *
     * @param calculatorParser the parser for strings
     */
    public CodingCalculator(CalculatorParser<String> calculatorParser) {
        super(calculatorParser);
    }

}
