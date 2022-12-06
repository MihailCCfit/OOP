package ru.nsu.fit.tsukanov.calculator.core.functions;

public interface CalculatorFunction<T>  {
     int getDimension();
     T calculate(T ... args);

     String getOperation();
}
