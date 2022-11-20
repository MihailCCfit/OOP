package studentsData.core;

import java.util.ArrayList;
import java.util.Collection;

public class Semester {
    private final ArrayList<Subject> subjects;
    private final long number;

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

    @Override
    public String toString() {
        return "Semester{" +
                "subjectMap=" + subjects +
                ", number=" + number +
                '}';
    }
}
