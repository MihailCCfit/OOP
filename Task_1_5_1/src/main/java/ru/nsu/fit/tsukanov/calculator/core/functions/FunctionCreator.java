package ru.nsu.fit.tsukanov.calculator.core.functions;

import java.util.function.Function;

public class FunctionCreator<T> {
    CalculatorFunction<T> createFunction(int ar, Function<T[], T> function){
        return new CalculatorFunction<T>() {
            @Override
            public int getDimension() {
                return ar;
            }

            @Override
            public T calculate(T[] arguments) {
                if (arguments == null){
                    throw new NullPointerException("Arguments is null");
                }
                if (arguments.length != ar){
                    throw new IllegalArgumentException("Num of number("+arguments.length
                            +") not equal to arity of function("+ar+")");
                }
                return function.apply(arguments);
            }

        };
    }
}
