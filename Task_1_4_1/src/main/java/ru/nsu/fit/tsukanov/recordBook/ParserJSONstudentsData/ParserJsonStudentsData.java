package ru.nsu.fit.tsukanov.recordBook.ParserJSONstudentsData;

import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import ru.nsu.fit.tsukanov.recordBook.core.RecordBook;
import ru.nsu.fit.tsukanov.recordBook.core.Semester;
import ru.nsu.fit.tsukanov.recordBook.core.Student;
import ru.nsu.fit.tsukanov.recordBook.core.Subject;


public class ParserJsonStudentsData {
    public static RecordBook recordBookParse(JSONObject specificRecordBook) {
        long id = (Long) specificRecordBook.get("id");


        Student student = studentParse((JSONObject) specificRecordBook.get("Student"));
        List<Semester> semesters = new ArrayList<>();
        JSONArray semestersArray = (JSONArray) specificRecordBook.get("Semesters");
        for (Object o : semestersArray) {
            semesters.add(semesterParse((JSONObject) o));
        }
        return new RecordBook(student, semesters, id);
    }

    public static Student studentParse(JSONObject specificPerson) {
        String surname = (String) specificPerson.get("surname");
        String name = (String) specificPerson.get("name");
        String department = (String) specificPerson.get("department");
        long group = (Long) specificPerson.get("group");
        String email = (String) specificPerson.get("email");
        return new Student(surname, name, department, group, email);
    }

    public static Semester semesterParse(JSONObject specificSemester) {
        long number = (long) specificSemester.get("number");
        ArrayList<Subject> subjects = new ArrayList<>();
        JSONArray subjectsArray = (JSONArray) specificSemester.get("subjects");
        for (Object subject : subjectsArray) {
            subjects.add(subjectParse((JSONObject) subject));
        }
        return new Semester(subjects, number);
    }

    public static Subject subjectParse(JSONObject specificSubject) {
        String mark = (String) specificSubject.get("Mark");
        String subjectName = (String) specificSubject.get("Subject");
        List<String> competencies = new ArrayList<>();
        for (Object competence : ((JSONArray) specificSubject.get("Competencies"))) {
            competencies.add((String) competence);
        }
        String date = (String) specificSubject.get("Date");
        String form = (String) specificSubject.get("Attestation form");
        List<String> teachers = new ArrayList<>();
        for (Object teacher : ((JSONArray) specificSubject.get("Teachers"))) {
            teachers.add((String) teacher);
        }
        int formattedMark;
        if (mark.equals("Pass")) {
            formattedMark = 1;
        } else {
            formattedMark = Integer.parseInt(mark);
            if (formattedMark < 2 || formattedMark > 5) {
                throw new IllegalStateException("Illegal mark value");
            }
        }
        return new Subject(subjectName, competencies,
                date, form, teachers, formattedMark);
    }
}
