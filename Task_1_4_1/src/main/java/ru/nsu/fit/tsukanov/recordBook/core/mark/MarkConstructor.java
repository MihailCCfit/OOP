package ru.nsu.fit.tsukanov.recordBook.core.mark;

import java.util.Objects;

public class MarkConstructor {
    public MarkConstructor() {
    }

    public Mark create(boolean notCreditMark, long rawValue, String representation) {
        return createMark(notCreditMark, rawValue, representation);
    }

    public static Mark createMark(boolean notCreditMark, long rawValue, String Representation) {
        return new Mark() {
            @Override
            public boolean notCreditMark() {
                return notCreditMark;
            }

            @Override
            public long getMark() {
                return rawValue;
            }

            @Override
            public String representation() {
                return Representation;
            }

            @Override
            public String toString() {
                return Representation;
            }

            @Override
            public int hashCode() {
                return Objects.hashCode(rawValue);
            }
        };
    }
}
