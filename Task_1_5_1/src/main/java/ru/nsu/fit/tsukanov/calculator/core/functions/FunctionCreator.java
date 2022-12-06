package ru.nsu.fit.tsukanov.calculator.core.functions;

import java.util.Objects;
import java.util.function.Function;

public class FunctionCreator {
    static public CalculatorFunction createFunction(int ar, String operation, Function<Object[], Object> function){
        if (ar<0){
            throw new IllegalArgumentException("Negative arity");
        }
        if (operation==null || function == null){
            throw new NullPointerException();
        }
        var v = new CalculatorFunction() {
            @Override
            public int getDimension() {
                return ar;
            }

            @Override
            public Object calculate(Object[] arguments) {
                if (arguments == null){
                    throw new NullPointerException("Arguments is null");
                }
                if (arguments.length != ar){
                    throw new IllegalArgumentException("Num of number("+arguments.length
                            +") not equal to arity of function("+ar+")");
                }
                return function.apply(arguments);
            }

            /**
             * @return operation
             */
            @Override
            public String getOperation() {
                return operation;
            }

        };
        return v;
    }
}
