package ru.nsu.fit.tsukanov.recordBook.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * Record book contains {@code id}, student and semesters.
 * And has functions for checking Scholarship and color of diploma.
 *
 * @see Student
 * @see Semester
 * @see Subject
 */
public class RecordBook {
    private long id;
    private final Student student;
    private final List<Semester> semesters;

    private long qualifyingMark = 0;

    /**
     * Creates Record book.
     *
     * @param student   the class Student with many fields
     * @param semesters the collection with semesters
     * @param id        the long, non-negative number
     */

    public RecordBook(Student student,
                      Collection<? extends Semester> semesters,
                      long id) {
        this(student, semesters, id, 0);
    }

    /**
     * Creates Record book.
     *
     * @param student        the class Student with many fields
     * @param semesters      the collection with semesters
     * @param id             the long, non-negative number
     * @param qualifyingMark the qualifyingMark at the end of study (2-5)
     */

    public RecordBook(Student student,
                      Collection<? extends Semester> semesters,
                      long id, long qualifyingMark) {
        this.student = student;
        this.semesters = new ArrayList<>(semesters);
        this.id = id;
        setQualifyingMark(qualifyingMark);
    }

    /**
     * Return student with many fields.
     *
     * @return student
     */
    public Student getStudent() {
        return student;
    }

    /**
     * Return semesters in the List.
     *
     * @return List of semesters
     */
    public List<Semester> getSemesters() {
        return semesters;
    }

    /**
     * Return Id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Set non-negative id.
     *
     * @param id the identification number
     */
    public void setId(long id) {
        if (id < 0) {
            throw new IllegalArgumentException("Negative id");
        }
        this.id = id;
    }

    public long getQualifyingMark() {
        return qualifyingMark;
    }

    /**
     * Set the mark for qualify work.
     * 0 - None. 2-5 - common mark.
     *
     * @param qualifyingMark the mark of qualify work.
     */
    public void setQualifyingMark(long qualifyingMark) {
        if ((qualifyingMark > 5 || qualifyingMark < 2) && qualifyingMark != 0) {
            throw new IllegalArgumentException("Illegal qualifyingMark");
        }
        this.qualifyingMark = qualifyingMark;
    }

    /**
     * Return short info about subject. Show only name and mark for each subject.
     *
     * @return short info about subjects.
     */
    public String shortInfo() {
        return "RecordBook (" + id + ") Student: "
                + student + "\n"
                + semesters.stream().map(Semester::shortInfo).reduce("", String::concat);
    }

    /**
     * Check fo higher Scholarship. If there is no bad mark (less than 4),
     * it would be higher scholarship.
     *
     * @return true if it would be higher scholarship
     */
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public boolean hasHighScholarship() {
        Semester lastSemester =
                semesters.stream()
                        .max(Comparator.comparingLong(Semester::getNumber))
                        .get();
        if (!isPassed()) {
            return false;
        }
        return lastSemester.getMarks().stream().allMatch((x) -> x > 3);
    }

    /**
     * Check for color of diploma (very useful).
     * If there is no bad mark (less than 4), and 75% of marks are five,
     * and qualifying work mark is five, it would be red Diploma.
     *
     * @return true if it would be red Diploma
     */
    public boolean hasRedDiploma() {
        @SuppressWarnings("OptionalGetWithoutIsPresent") Semester lastSemester =
                semesters.stream()
                        .max(Comparator.comparingLong(Semester::getNumber))
                        .get();
        return isPassed()
                && lastSemester.getMarks().stream().allMatch((x) -> x > 3)
                && getAverage() >= 4.75 && qualifyingMark == 5;
    }

    /**
     * Calculates average mark from all semesters.
     *
     * @return average mark from all semesters
     */
    public double getAverage() {
        List<Long> marksFromAll = getMarks();
        if (marksFromAll.isEmpty()) {
            return 0;
        }
        return (double) marksFromAll
                .stream()
                .reduce(Long::sum)
                .get()
                / marksFromAll.size();
    }

    /**
     * Return all marks (without pass or fail).
     *
     * @return list of marks
     */
    public List<Long> getMarks() {
        List<Long> marksFromAll = new ArrayList<>();
        semesters.stream().map(Semester::getMarks).forEach(marksFromAll::addAll);
        return marksFromAll;
    }

    /**
     * Check for passed all subjects.
     *
     * @return true, if it's alright
     */
    public boolean isPassed() {
        return semesters.stream().map(Semester::checkPassed).reduce(true, (x, y) -> x && y);
    }

    /**
     * Return cool string representation.
     *
     * @return cool string representation
     */
    @Override
    public String toString() {
        return "RecordBook{"
                + "id=" + id
                + ", student=" + student
                + ", semesters=" + semesters
                + '}';
    }
}
