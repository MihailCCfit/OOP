package studentsData.core;

import java.util.List;

public class RecordBook {
    private final Student student;
    private final List<Semester> semesters;
    private long id;


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
        this.id = id;
    }
}
