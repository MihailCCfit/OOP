package ru.nsu.fit.tsukanov.recordBook.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The class that contains subjects and number of this semester.
 * This class is used for data storage for record book.
 *
 * @see RecordBook
 * @see Subject
 */
public class Semester {
    private final List<Subject> subjects;
    private final long number;

    /**
     * Construct semester with subjects and semester number.
     *
     * @param subjects the collection of subjects
     * @param number   the number of semester
     * @see Subject
     */
    public Semester(Collection<Subject> subjects, long number) {
        this.number = number;
        this.subjects = new ArrayList<>();
        this.subjects.addAll(subjects);
    }

    /**
     * Return list of subjects.
     *
     * @return list of subjects
     * @see Subject
     */
    public List<Subject> getSubjects() {
        return subjects;
    }

    /**
     * Return number of semester.
     *
     * @return number of semester
     */
    public long getNumber() {
        return number;
    }

    /**
     * Return short information about all subjects in this semester.
     *
     * @return string of short information
     * @see Subject#shortResult()
     */
    public String shortInfo() {
        return "Semester(" + number
                + ") subjects:\n" + subjects.stream()
                .map(Subject::shortResult)
                .map((x) -> x.concat("\n"))
                .reduce(String::concat).orElse("");
    }

    /**
     * Return list of marks (without pass or fail).
     *
     * @return list of marks
     */
    public List<Long> getMarks() {
        return subjects.stream()
                .map(Subject::getMarkRaw)
                .filter((x) -> x > 1)
                .collect(Collectors.toList());
    }

    /**
     * Check this semester for pass all subjects.
     *
     * @return return true if all right
     */
    public boolean checkPassed() {
        return subjects.stream()
                .map(Subject::getMarkRaw)
                .allMatch((x) -> x != 0 && x != 2);
    }

    /**
     * Cool string representation, with all subjects.
     *
     * @return cool string representation
     */
    @Override
    public String toString() {
        return "Semester(" + number
                + ") subjects:\n" +
                subjects.stream()
                        .map((x)->x.toString()+"\n")
                        .reduce(String::concat).orElse("");
    }
}
