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
    public static final int tableSize = 70;

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
        return new ArrayList<>(subjects);
    }

    /**
     * Add the subject to the list of subjects.
     * If there is no the same subjects,
     * then specified subject will be added.
     *
     * @param subject the semester that will be added into list of semesters
     * @return true if subject was added
     */
    public boolean addSubject(Subject subject) {
        if (subjects.stream().anyMatch((subj) -> subj.equals(subject))) {
            return false;
        }
        subjects.add(subject);
        return true;
    }

    /**
     * @param inputSubjects the collection of subjects
     *                      that will be added into the list of subjects.
     */
    public void addSubjects(Collection<Subject> inputSubjects) {
        for (Subject subject : inputSubjects) {
            addSubject(subject);
        }
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
     * @see Subject#shortInfo()
     */
    public String shortInfo() {
        return "Semester(" + number
                + ") subjects amount: " + subjects.size()
                + " average: " + getAverage();
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
     * get average mark of this semester.
     *
     * @return average from semester
     */
    public double getAverage() {
        List<Long> marks = getMarks();
        if (marks.isEmpty()) {
            return 0;
        }
        return (double) marks
                .stream()
                .reduce(Long::sum)
                .get()
                / marks.size();
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
                + ") subjects:\n"
                + subjects.stream()
                .map((x) -> x.toString() + "\n")
                .reduce(String::concat).orElse("");
    }
}
