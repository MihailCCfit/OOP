package ru.nsu.fit.tsukanov.calculator.core.functions;

import java.util.List;

public interface CalculatorFunction<T>  {
     int getDimension();
     T calculate(List<T> list);
}
