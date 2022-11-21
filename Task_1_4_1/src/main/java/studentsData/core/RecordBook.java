package studentsData.core;

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

    public RecordBook(Student student, Collection<? extends Semester> semesters, long id) {
        this.student = student;
        this.semesters = new ArrayList<>(semesters);
        this.id = id;
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

    public void setQualifyingMark(long qualifyingMark) {
        this.qualifyingMark = qualifyingMark;
    }

    /**
     * Return short info about subject. Show only name and mark for each subject.
     *
     * @return short info about subjects.
     */
    public String shortInfo() {
        StringBuilder sems = new StringBuilder();
        for (Semester semester : semesters) {
            sems.append(semester.shortInfo());
        }
        return "RecordBook (" + id + ") Student: "
                + student + "\n"
                + sems;
    }

    /**
     * Check fo higher Scholarship. If there is no bad mark (less than 4), it would be higher scholarship.
     *
     * @return true if it would be higher scholarship
     */
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public boolean hasHighScholarship() {
        Semester lastSemester =
                semesters.stream()
                        .max(Comparator.comparingLong(Semester::getNumber))
                        .get();
        if (lastSemester.getMarks().contains(null)) {
            return false;
        }
        return lastSemester.getMarks().stream().allMatch((x) -> x > 3);
    }

    /**
     * Check for color of diploma (very useful). If there is no bad mark (less than 4), and 75% of marks are five,
     * and qualifying work mark is five, it would be red Diploma.
     *
     * @return true if it would be red Diploma
     */
    public boolean hasRedDiploma() {
        Semester lastSemester =
                semesters.stream()
                        .max(Comparator.comparingLong(Semester::getNumber))
                        .get();
        var marks = lastSemester.getMarks();
        if (marks.contains(null)) {
            return false;
        }
        return lastSemester.getMarks().stream().allMatch((x) -> x > 3)
                && getAverage() >= 4.75 && qualifyingMark == 5;
    }

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

    public List<Long> getMarks() {
        List<Long> marksFromAll = new ArrayList<>();
        for (Semester semester : semesters) {
            marksFromAll.addAll(semester.getMarks());
        }
        return marksFromAll;
    }

    @Override
    public String toString() {
        return "RecordBook{" +
                "id=" + id +
                ", student=" + student +
                ", semesters=" + semesters +
                '}';
    }
}
