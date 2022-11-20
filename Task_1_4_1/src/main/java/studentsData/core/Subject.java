package studentsData.core;

import java.util.*;

public class Subject {
    private final String subjectName;
    private final List<String> competencies;
    private String certificationDate;
    private final String attestationForm;
    private List<String> teachers;
    private long mark;

    public Subject(String subjectName, Collection<String> competencies,
                   String certificationDate, String attestationForm,
                   Collection<String> teachers, long mark) {
        this.competencies = new ArrayList<>(competencies);
        this.subjectName = subjectName;
        this.certificationDate = certificationDate;
        this.attestationForm = attestationForm;
        if (teachers != null) {
            this.teachers = new ArrayList<>(teachers);
        } else {
            this.teachers = new ArrayList<>();
        }
        this.mark = mark;
    }

    public Subject(String subjectName, Collection<String> competencies, String attestationForm) {
        this(subjectName, competencies, "", attestationForm, null, 1);
    }

    public String getSubjectName() {
        return subjectName;
    }

    public List<String> getCompetencies() {
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

    public List<String> getTeachers() {
        return teachers;
    }

    public void setTeachers(Collection<String> teachers) {
        this.teachers = new ArrayList<>(teachers);
    }

    public boolean addTeacher(String newTeacher) {
        if (teachers.contains(newTeacher)) {
            return false;
        }
        teachers.add(newTeacher);
        return true;
    }

    long _getMark() {
        return mark;
    }
    public String getMark(){
        String markString;
        if (mark==0 || mark == 1) {
            markString = mark==1? "Passed" : "Not Passed";
        } else if (mark<2 || mark>5){
            markString = "-";
        }   else{
            markString = "" + mark;
        }
        return markString;
    }

    public void setMark(int mark) {
        if (mark < 2 || mark > 5) {
            throw new IllegalArgumentException("mark should be between 2 and 5");
        }
        this.mark = mark;
    }

    @Override
    public String toString() {
        String markString;
        if (mark==0 || mark == 1) {
            markString = mark==1? "Passed" : "Not Passed";
        } else if (mark<2 || mark>5){
            markString = "-";
        }   else{
            markString = ""+mark;
        }

        return "Subject (" +
                subjectName +
                ")\n competencies: " + competencies +
                "\n certificationDate: " + (certificationDate.isEmpty()? "-" : certificationDate) +
                "\n attestationForm: " + attestationForm +
                "\n teachers: " + teachers +
                "\n mark " + markString;
    }

    public String shortResult() {
        return "" + subjectName + ": " + mark + "";
    }
}
