package ru.nsu.fit.tsukanov.calculator.core.calculators;

import ru.nsu.fit.tsukanov.calculator.core.functions.CalculatorFunction;
import ru.nsu.fit.tsukanov.calculator.core.functions.FunctionCreator;

import java.util.LinkedList;
import java.util.List;

public class CalculatorCell<Numb> {
    private final LinkedList<Numb> stack = new LinkedList<>();
    private final CalculatorFunction<Numb> calculatorFunction;
    CalculatorCell(CalculatorFunction<Numb> calculatorFunction){
        this.calculatorFunction = calculatorFunction;
    }
    boolean isExecutable(){

        return calculatorFunction.getDimension()==stack.size();
    }

    boolean push(Number number){
        stack.p
    }
}
