package ru.nsu.fit.tsukanov.recordBook.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Class for collecting data about subject in the recordBook of one person.
 */
public class Subject {
    private final String subjectName;
    private final List<String> competencies;
    private String certificationDate;
    private final String attestationForm;
    private List<String> teachers;
    /**
     * Mark: 0 - failed, 1 - passed, 2-5 - common mark.
     */
    private long mark;

    /**
     * Construct subject.
     * Mark: 0 - failed, 1 - passed, 2-5 - common mark.
     *
     * @param subjectName       the name of subject
     * @param competencies      the competencies
     * @param certificationDate the date when person passed or got mark.
     * @param attestationForm   the form of passing the subjects
     * @param teachers          the persons who taught owner of this
     * @param mark              the specific mark
     */
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

    /**
     * Add teacher into the subject's list of teachers
     *
     * @param newTeacher new teacher for adding
     * @return true if there is adding to the list.
     */
    public boolean addTeacher(String newTeacher) {
        if (teachers.contains(newTeacher)) {
            return false;
        }
        teachers.add(newTeacher);
        return true;
    }

    /**
     * Return long value (raw representation) of mark.
     *
     * @return long value (raw representation) of mark.
     */
    public long getMarkRaw() {
        return mark;
    }

    /**
     * Return right representation, without 0 and 1.
     *
     * @return string representation
     */
    public String getMarkString() {
        String markString;
        if (mark == 0 || mark == 1) {
            markString = mark == 1 ? "Passed" : "Failed";
        } else {
            markString = "" + mark;
        }
        return markString;
    }

    /**
     * Set mark to specified mark. If mark==1 - Passed, if mark == 0 - failed
     * mark between 2 and 5 means the same. Other value for mark are invalid.
     *
     * @param mark the mark value for setting mark.
     */
    public void setMark(long mark) {
        if (mark < 0 || mark > 5) {
            throw new IllegalArgumentException("mark should be between 2 and 5");
        }
        this.mark = mark;
    }

    /**
     * Cool string representation of subject, with all information.
     * It's contains subjects name, competencies, certificationDate, attestationForm, teachers, mark
     *
     * @return cool string representation
     */
    @Override
    public String toString() {
        String markString;
        if (mark == 0 || mark == 1) {
            markString = mark == 1 ? "Passed" : "Not Passed";
        } else if (mark < 2 || mark > 5) {
            markString = "-";
        } else {
            markString = "" + mark;
        }

        return "Subject (" +
                getSubjectName()
                + ")\n competencies: " + getCompetencies()
                + "\n certificationDate: " + (certificationDate.isEmpty() ?
                "-" : getCertificationDate())
                + "\n attestationForm: " + getAttestationForm()
                + "\n teachers: " + getTeachers()
                + "\n mark " + markString;
    }

    /**
     * Return string of short result of this subject (subject name and mark).
     *
     * @return string with short result of subject
     */
    public String shortResult() {
        return "" + subjectName + ": " + getMarkString() + "";
    }
}
