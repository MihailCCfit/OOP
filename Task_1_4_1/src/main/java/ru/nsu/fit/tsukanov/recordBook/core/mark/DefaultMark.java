package ru.nsu.fit.tsukanov.recordBook.core.mark;

public enum DefaultMark {
    FAILED(false, -1, "FAILED"),
    PASSED(false, 1, "PASSED"),
    UNSATISFACTORY(true, 2, "2"),
    SATISFACTORY(true, 3, "3"),
    GOOD(true, 4, "4"),
    EXCELLENT(true, 5, "5");
    public final boolean notCreditMark;
    public final long rawValue;
    public final String representation;

    DefaultMark(boolean notCreditMark, long rawValue, String representation) {
        this.notCreditMark = notCreditMark;
        this.rawValue = rawValue;
        this.representation = representation;
    }

    public Mark toMark() {
        return mark(this);
    }

    static public Mark mark(DefaultMark enumMark) {
        return MarkConstructor.createMark(enumMark.notCreditMark, enumMark.rawValue, enumMark.representation);
    }

    static public DefaultMark toEnumMark(String str) {
        return switch (str) {
            case "FAILED" -> FAILED;
            case "PASSED" -> PASSED;
            case "2" -> UNSATISFACTORY;
            case "3" -> SATISFACTORY;
            case "4" -> GOOD;
            case "5" -> EXCELLENT;
            default -> throw new IllegalArgumentException(str);
        };
    }

    static public Mark createMarkFromString(String str) {
        return toEnumMark(str).toMark();
    }

}
