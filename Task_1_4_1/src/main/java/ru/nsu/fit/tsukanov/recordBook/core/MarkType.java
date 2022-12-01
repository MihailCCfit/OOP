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

    MarkType(String str) {
        representation = str;
    }

    public boolean notCreditMark() {
        return switch (this) {
            case FAILED, PASSED -> false;
            default -> true;
        };
    }

    public long rawMark() {
        return switch (this) {
            case FAILED -> 0;
            case PASSED -> 1;
            case UNSATISFACTORY -> 2;
            case SATISFACTORY -> 3;
            case GOOD -> 4;
            case EXCELLENT -> 5;
        };
    }

    @Override
    public String toString() {
        return switch (this) {
            case FAILED -> "FAILED";
            case PASSED -> "PASSED";
            case UNSATISFACTORY -> "2";
            case SATISFACTORY -> "3";
            case GOOD -> "4";
            case EXCELLENT -> "5";
        };
    }

    static public MarkType createMark(String inputString) {
        MarkType mark = switch (inputString) {
            case "PASSED" -> MarkType.PASSED;
            case "FAILED" -> MarkType.FAILED;
            case "2", "UNSATISFACTORY" -> UNSATISFACTORY;
            case "3", "SATISFACTORY" -> SATISFACTORY;
            case "4", "GOOD" -> GOOD;
            case "5", "EXCELLENT" -> EXCELLENT;
            default -> null;
        };
        if (mark == null) {
            throw new IllegalArgumentException(inputString + "- illegal argument");
        }
        return mark;
    }

}
