package ru.nsu.fit.tsukanov.recordBook.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The class that contains subjects and number of this semester.
 * This class is used for data storage for record book.
 *
 * @see RecordBook
 * @see Subject
 */
public class Semester {
    private final ArrayList<Subject> subjects;
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


    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

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
        StringBuilder ans = new StringBuilder();
        for (Subject subject : subjects) {
            ans.append(subject.shortResult()).append("\n");
        }
        return "Semester(" + number
                + ") subjects:\n" + ans;
    }

    /**
     * Return list of marks (without pass or fail).
     *
     * @return list of marks
     */
    public List<Long> getMarks() {
        List<Long> marks = new ArrayList<>();
        for (Subject subject : subjects) {
            long mark = subject.getMarkRaw();
            if (mark > 1) {
                marks.add(mark);
            }
        }
        return marks;
    }

    /**
     * Check this semester for pass all subjects.
     *
     * @return return true if all right
     */
    public boolean checkPassed() {
        for (Subject subject : subjects) {
            if ((subject.getMarkRaw() == 0) || (subject.getMarkRaw() == 2)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Cool string representation, with all subjects.
     *
     * @return cool string representation
     */
    @Override
    public String toString() {
        StringBuilder subs = new StringBuilder();
        for (Subject subject : subjects) {
            subs.append(subject).append("\n");
        }
        return "Semester(" + number
                + ") subjects:\n" + subs;
    }
}
