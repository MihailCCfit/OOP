package ru.nsu.fit.tsukanov.recordBook.ParserJSONstudentsData;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import ru.nsu.fit.tsukanov.recordBook.core.*;


/**
 * Json-Parser for specified json file, that has specified format.
 */
public class ParserJsonStudentsData {
    private ParserJsonStudentsData() {

    }

    /**
     * Parser JSON for recordBook.
     * JSON format:
     * "id": (Long) id
     * "Student": (Student) student
     * "Semesters": (Semester[]) semesters
     *
     * @param specificRecordBook the jsonObject that has previous structure
     * @return new Record book rely on JSONObject
     * @see Student
     * @see Semester
     * @see ParserJsonStudentsData#studentParse(JSONObject)
     * @see ParserJsonStudentsData#semesterParse(JSONObject)
     */
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

    /**
     * Parser JSON for student.
     * JSON format:
     * "surname": (String) surname
     * "name": (String) name
     * "department": (String) department
     * "group": (String) group
     * "email": (String) email
     *
     * @param specificPerson the jsonObject that has previous structure
     * @return new Student book rely on JSONObject
     * @see Student
     */
    public static Student studentParse(JSONObject specificPerson) {
        String surname = (String) specificPerson.get("surname");
        String name = (String) specificPerson.get("name");
        String patronymic = (String) specificPerson.get("patronymic");
        String department = (String) specificPerson.get("department");
        long group = (Long) specificPerson.get("group");
        String email = (String) specificPerson.get("email");
        return new Student(surname, name, patronymic,department, group, email);
    }

    /**
     * Parser JSON for semester.
     * JSON format:
     * "number": (Long) number
     * "subjects": (Subject[]) subjects
     *
     * @param specificSemester the jsonObject that has previous structure
     * @return new Semester book rely on JSONObject
     * @see Subject
     * @see Semester
     * @see ParserJsonStudentsData#subjectParse(JSONObject)
     */
    @SuppressWarnings("unchecked")
    public static Semester semesterParse(JSONObject specificSemester) {
        long number = (long) specificSemester.get("number");
        JSONArray subjectsArray = (JSONArray) specificSemester.get("subjects");
        ArrayList<Subject> subjects = (ArrayList<Subject>) subjectsArray.stream()
                .map(x -> subjectParse((JSONObject) x))
                .collect(Collectors.toCollection(ArrayList::new));
        return new Semester(subjects, number);
    }

    /**
     * JSON format:
     * "Mark": (String) mark
     * "Subject": (String) subjectName
     * "Competencies": (String[]) competencies
     * "Date": (String) date
     * "Attestation form": (String) email
     * "Teachers": (String[]) teachers
     * Mark is converted in the certain format.
     *
     * @param specificSubject the jsonObject that has previous structure
     * @return new Subject book rely on JSONObject
     * @see Subject#getMarkString()
     * @see Subject#getMarkRaw()
     */
    public static Subject subjectParse(JSONObject specificSubject) {
        final String mark = (String) specificSubject.get("Mark");
        final String subjectName = (String) specificSubject.get("Subject");
        final List<String> competencies = new ArrayList<>();
        for (Object competence : ((JSONArray) specificSubject.get("Competencies"))) {
            competencies.add((String) competence);
        }
        final String date = (String) specificSubject.get("Date");
        final String form = (String) specificSubject.get("Attestation form");
        final List<String> teachers = new ArrayList<>();
        for (Object teacher : ((JSONArray) specificSubject.get("Teachers"))) {
            teachers.add((String) teacher);
        }
        return new Subject(subjectName, competencies,
                date, form, teachers, mark);
    }
}
