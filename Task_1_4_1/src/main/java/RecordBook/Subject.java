package RecordBook;

import java.util.*;

public class Subject {
    private final String subjectName;
    private final ArrayList<String> competencies;
    private String certificationDate;
    private final String attestationForm;
    private Set<String> teachers;
    private int mark;

    public Subject(String subjectName, Collection<String> competencies,
                   String certificationDate, String attestationForm,
                   Collection<String> teachers, int mark) {
        this.competencies = new ArrayList<>(competencies);
        this.subjectName = subjectName;
        this.certificationDate = certificationDate;
        this.attestationForm = attestationForm;
        if (teachers != null){
            this.teachers = new TreeSet<>(teachers);
        }
        else{
            this.teachers = new TreeSet<>();
        }
        this.mark = mark;
    }
    public Subject(String subjectName, Collection<String> competencies, String attestationForm){
        this(subjectName, competencies, "", attestationForm, null, 0);
    }

    public String getSubjectName() {
        return subjectName;
    }

    public ArrayList<String> getCompetencies() {
        return competencies;
    }

    public String getCertificationDate() {
        return certificationDate;
    }

    public void setCertificationDate(String certificationDate) {
        this.certificationDate = certificationDate;
    }

    public String getAttestationForm() {
        return attestationForm;
    }

    public Set<String> getTeachers() {
        return teachers;
    }

    public void setTeachers(Collection<String> teachers) {
        this.teachers = new TreeSet<>(teachers);
    }
    public boolean addTeacher(String newTeacher){
        if (teachers.contains(newTeacher)){
            return false;
        }
        teachers.add(newTeacher);
        return true;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        if (mark<2 || mark>5){
            throw new IllegalArgumentException("mark should be between 2 and 5");
        }
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "Subject (" +
                subjectName+
                ")\n competencies: " + competencies +
                "\n certificationDate: " + certificationDate  +
                "\n attestationForm: " + attestationForm  +
                "\n teachers: " + teachers +
                "\n mark " + mark;
    }
    public String shortResult(){
        return "" + subjectName + ": " + mark + "";
    }
}
