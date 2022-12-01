package ru.nsu.fit.tsukanov.recordBook.core;

/**
 * Types of Mark
 */
public enum MarkType {
    FAILED("FAILED"),
    PASSED("PASSED"),
    UNSATISFACTORY("2"),
    SATISFACTORY("3"),
    GOOD("4"),
    EXCELLENT("5");
    public final String representation;
    MarkType(String str){
        representation = str;
    }

    public boolean isCreditMark(){
        return switch (this){
            case FAILED, PASSED -> true;
            default -> false;
        };
    }
    long rawMark(){
        return switch (this){
            case FAILED -> 0;
            case PASSED -> 1;
            case UNSATISFACTORY -> 2;
            case SATISFACTORY -> 3;
            case GOOD ->  4;
            case EXCELLENT -> 5;
        };
    }

    @Override
    public String toString() {
        return switch (this){
            case FAILED -> "FAILED";
            case PASSED -> "PASSED";
            case UNSATISFACTORY -> "2";
            case SATISFACTORY -> "3";
            case GOOD ->  "4";
            case EXCELLENT -> "5";
        };
    }
}
