package ru.nsu.fit.tsukanov.calculator.real;

import ru.nsu.fit.tsukanov.calculator.core.parser.CalculatorParser;
import ru.nsu.fit.tsukanov.calculator.real.parsers.RealFunctionParser;
import ru.nsu.fit.tsukanov.calculator.real.parsers.RealNumberParser;
import ru.nsu.fit.tsukanov.calculator.template.stack.StackCalculatorTemplate;

public class RealCalculator extends StackCalculatorTemplate<Double> {

    public RealCalculator() {
        this(new CalculatorParser<>(new RealNumberParser(), RealFunctionParser.getParser()));
    }

    /**
     * Creates stackCalculator, that has stack of functionWrapper. functionWrapper saves arguments
     * while it isn't available.
     *
     * @param calculatorParser the nothing
     */
    public RealCalculator(CalculatorParser<Double> calculatorParser) {
        super(calculatorParser);
    }
}
