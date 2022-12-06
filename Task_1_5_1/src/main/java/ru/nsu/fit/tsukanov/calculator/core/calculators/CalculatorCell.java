package ru.nsu.fit.tsukanov.calculator.core.calculators;

import ru.nsu.fit.tsukanov.calculator.core.functions.CalculatorFunction;
import ru.nsu.fit.tsukanov.calculator.core.functions.FunctionCreator;

import java.util.LinkedList;
import java.util.List;

public class CalculatorCell<Numb> {
    private final List<Numb> stack = new LinkedList();
    private CalculatorFunction calculatorFunction;
    CalculatorCell(CalculatorFunction calculatorFunction){
        this.calculatorFunction = calculatorFunction;
    }
    boolean isExecutable(){

        return false;
    }
}
