package ru.nsu.fit.tsukanov.recordBook.core.mark;

public interface Mark {
    /**
     * Check for credit mark.
     *
     * @return true if mark is not credit mark.
     */
    boolean notCreditMark();

    /**
     * If mark isn't credit mark, then non-negative is good, negative is academic doubt.
     *
     * @return long value of mark.
     */
    long getMark();

    /**
     * Representation.
     *
     * @return string of mark representation.
     */
    String representation();

}
