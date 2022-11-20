package studentsData.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
    public String shortInfo(){
        String ans = "";
        for (Subject subject : subjects) {
            ans += subject.shortResult() + "\n";
        }
        return "Semester(" + number
                + ") subjects:\n" + ans;
    }

    public List<Long> getMarks(){
        List<Long> marks = new ArrayList<>();
        for (Subject subject : subjects) {
            long mark = subject._getMark();
            if (mark==0){
                throw new IllegalStateException("LOX, FAILED EXAM");
            }
            if (mark!=1){
                marks.add(mark);
            }
        }
        return marks;
    }

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
