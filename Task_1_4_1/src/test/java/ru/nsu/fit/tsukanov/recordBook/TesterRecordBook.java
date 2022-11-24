package ru.nsu.fit.tsukanov.recordBook;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.fit.tsukanov.recordBook.ParserJSONstudentsData.ParserJsonStudentsData;
import ru.nsu.fit.tsukanov.recordBook.core.RecordBook;
import ru.nsu.fit.tsukanov.recordBook.core.Student;
import ru.nsu.fit.tsukanov.recordBook.core.Subject;



/**
 * Check RecordBook, ParserJSON, and components of RecordBook.
 */
public class TesterRecordBook {
    @Test
    void testJson() throws IOException, ParseException {
        FileReader fileReader;
        fileReader = new FileReader("src/test/resources/js.json");
        JSONParser jsonParser = new JSONParser();
        JSONObject object = (JSONObject) jsonParser.parse(fileReader);

        RecordBook recordBook = ParserJsonStudentsData.recordBookParse(
                object);
        Assertions.assertTrue(recordBook.hasHighScholarship());
        Assertions.assertTrue(recordBook.getAverage() > 4);
        System.out.println(recordBook.shortInfo());
        System.out.println(recordBook);
        try (Writer out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("output.txt"), StandardCharsets.UTF_8))) {
            out.write(recordBook.toString());
        }

        Student me = new Student("Tsukanov", "Mikhail",
                "FIT", 21214, "m.tsukanov@g.nsu.ru");
        Assertions.assertEquals(me, recordBook.getStudent());
        Assertions.assertEquals(me.hashCode(), recordBook.getStudent().hashCode());

        me.setEmail("ru.ru");
        Assertions.assertNotEquals(me, recordBook.getStudent());
        me.setGroup(3228);
        Assertions.assertNotEquals(me, recordBook.getStudent());
        me.setDepartment("CCFIT");
        Assertions.assertNotEquals(me, recordBook.getStudent());
        me.setName("Ba");
        Assertions.assertNotEquals(me, recordBook.getStudent());
        me.setSurname("Abo");
        Assertions.assertNotEquals(me, recordBook.getStudent());
        Assertions.assertEquals(me, me);
        Assertions.assertEquals(me.toString(),
                "Student[surname=Abo, name=Ba, "
                        + "department=CCFIT, group=3228, mail=ru.ru]");
        for (Subject subject : recordBook.getSemesters().get(1).getSubjects()) {
            if (!subject.getMarkString().equals("5")) {
                subject.setMark(5);
            }
        }
        Assertions.assertEquals(recordBook.getAverage(), 5);
        Subject someSubj = recordBook.getSemesters()
                .get(1).getSubjects()
                .get(0);
        Assertions.assertTrue(someSubj.addTeacher("HaHaHa"));
        Assertions.assertFalse(recordBook.getSemesters()
                .get(1).getSubjects()
                .get(0).addTeacher("HaHaHa"));
        someSubj.setMark(5);
        someSubj.setCertificationDate("777.777.777");
        someSubj.setTeachers(List.of("Amogus"));
        Assertions.assertFalse(recordBook.getId() < 0);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> recordBook.setId(-4));
        recordBook.setId(777);
        Assertions.assertEquals(recordBook.getId(), 777);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> someSubj.setMark(-5));
        someSubj.setMark(1);
        Assertions.assertEquals(someSubj.getMarkString(), "Passed");
        Assertions.assertFalse(recordBook.hasRedDiploma());
        recordBook.setQualifyingMark(5);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> recordBook.setQualifyingMark(-5));
        Assertions.assertEquals(5, recordBook.getQualifyingMark());
        Assertions.assertTrue(recordBook.hasRedDiploma());
        someSubj.setMark(0);
        Assertions.assertEquals(someSubj.getMarkString(), "Failed");
        Assertions.assertFalse(recordBook.hasRedDiploma());
        Assertions.assertFalse(recordBook.hasHighScholarship());
        someSubj.setMark(3);
        Assertions.assertEquals(someSubj.getMarkString(), "3");
        recordBook.getSemesters().clear();
        Assertions.assertEquals(recordBook.getAverage(), 0);
    }

    @Test
    void someParserTests() throws ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject object = (JSONObject) jsonParser.parse("{\n"
                + "          \"Mark\": \"-5\",\n"
                + "          \"Subject\": \"\\u0412\\u0432\\u0435\\u0434\\u0435\\u043d\\u0438\\u0435 " +
                "\\u0432 \\u0434\\u0438\\u0441\\u043a\\u0440\\u0435\\u0442\\u043d\\u0443\\u044e " +
                "\\u043c\\u0430\\u0442\\u0435\\u043c\\u0430\\u0442\\u0438\\u043a\\u0443 \\u0438 " +
                "\\u043c\\u0430\\u0442\\u0435\\u043c\\u0430\\u0442\\u0438\\u0447\\u0435\\u0441\\u043a\\u0443\\u044e " +
                "\\u043b\\u043e\\u0433\\u0438\\u043a\\u0443\",\n"
                + "          \"Competencies\": [\n"
                + "            \"\\u041e\\u041f\\u041a-1\"\n"
                + "          ],\n"
                + "          \"Date\": \"18.01.2022\",\n"
                + "          \"Attestation form\": \"\\u042d\\u043a\\u0437\\u0430\\u043c\\u0435\\u043d\",\n"
                + "          \"Teachers\": [\n"
                + "            \"\\u0412\\u043b\\u0430\\u0441\\u043e\\u0432 " +
                "\\u0414\\u043c\\u0438\\u0442\\u0440\\u0438\\u0439 " +
                "\\u042e\\u0440\\u044c\\u0435\\u0432\\u0438\\u0447\"\n"
                + "          ]\n"
                + "        }");
        Assertions.assertThrows(IllegalStateException.class, () ->
                ParserJsonStudentsData.subjectParse(object));
    }

    @Test
    void someSubjectTests() {
        Subject subject = new Subject("C",
                List.of("OPK-12"), "12.12.2012",
                "test", null, 1);
        Assertions.assertTrue(subject.getTeachers().isEmpty());
        Assertions.assertTrue(subject.addTeacher("Gatilov Stepan Urievich"));
        Assertions.assertEquals("Passed", subject.getMarkString());
        subject.setMark(0);
        Assertions.assertEquals("Failed", subject.getMarkString());
        Assertions.assertTrue(subject.toString().contains("Failed"));
    }

    @Test
    void someStudentTests() {
        Student student = new Student("a", "b", "c",
                3228, "@");
        Assertions.assertFalse(student.equals("aboba"));
    }
}
