package ru.nsu.fit.tsukanov.calculator.core.functions;

public enum Operation {
    PLUS("+"),
    MINUS("-"),
    DIV("/"),
    MUL("*"),
    POW("^"),
    SIN("sin"),
    COS("cos");


    public final String str;
    Operation(String str){
        this.str = str;
    }
}
