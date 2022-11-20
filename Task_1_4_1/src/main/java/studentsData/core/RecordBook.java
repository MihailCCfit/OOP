package studentsData.core;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RecordBook {
    private long id;
    private final Student student;
    private final List<Semester> semesters;


    public RecordBook(Student student, List<Semester> semesters, long id) {
        this.student = student;
        this.semesters = semesters;
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public List<Semester> getSemesters() {
        return semesters;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        if (id<0){
            throw new IllegalArgumentException("Negative id");
        }
        this.id = id;
    }

    public String shortInfo() {
        StringBuilder sems = new StringBuilder();
        for (Semester semester : semesters) {
            sems.append(semester.shortInfo());
        }
        return "RecordBook (" + id + ") Student: "
                + student + "\n"
                + sems;
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public boolean hasHighScholarship() {
        Semester lastSemester =
                semesters.stream()
                        .max(Comparator.comparingLong(Semester::getNumber))
                        .get();
        return lastSemester.getMarks().stream().allMatch((x) -> x > 3);
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
