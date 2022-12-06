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
import ru.nsu.fit.tsukanov.recordBook.core.*;



/**
 * Check RecordBook, ParserJSON, and components of RecordBook.
 */
public class RecordBookTest {
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
        try (Writer out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("output.txt"), StandardCharsets.UTF_8))) {
            out.write(TablePrinter.recordBookInfo(recordBook));
            out.flush();
        }
        try (Writer out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("src/test/resources/output.txt"), StandardCharsets.UTF_8))) {

            out.write(TablePrinter.recordBookInfo(recordBook));
            out.flush();
        }

        Student me = new Student("Tsukanov", "Mikhail", "Aleksandrov",
                "FIT", 21214, "m.tsukanov@g.nsu.ru");
        Assertions.assertEquals(me, recordBook.getStudent());
        Assertions.assertEquals(me.hashCode(), recordBook.getStudent().hashCode());
        Assertions.assertEquals(me, me);
        Assertions.assertNotEquals(me.toString(),
                "Student[surname=Abo, name=Ba, "
                        + "department=CCFIT, group=3228, mail=ru.ru]");
        for (Subject subject : recordBook.getSemesters().get(1).getSubjects()) {
            if (!subject.getMarkString().equals("5")) {
                subject.setMark(MarkType.EXCELLENT);
            }
        }
        Assertions.assertEquals(recordBook.getAverage(), 5);
        Subject someSubj = recordBook.getSemesters()
                .get(1).getSubjects()
                .get(0);

        var semesters = recordBook.getSemesters();
        var oneSemester = semesters.get(0);
        Assertions.assertFalse(recordBook.addSemester(recordBook.getSemesters().get(0)));
        recordBook.addSemesters(recordBook.getSemesters());
        Assertions.assertFalse(oneSemester.addSubject(oneSemester.getSubjects().get(0)));
        oneSemester.addSubjects(oneSemester.getSubjects());

        Assertions.assertTrue(someSubj.addTeacher("HaHaHa"));
        Assertions.assertFalse(recordBook.getSemesters()
                .get(1).getSubjects()
                .get(0).addTeacher("HaHaHa"));
        someSubj.setMark(MarkType.EXCELLENT);
        someSubj.setCertificationDate("777.777.777");
        someSubj.setTeachers(List.of("Amogus"));
        Assertions.assertFalse(recordBook.getId() < 0);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> recordBook.setId(-4));
        recordBook.setId(777);
        Assertions.assertEquals(recordBook.getId(), 777);
        someSubj.setMark(MarkType.PASSED);
        Assertions.assertEquals(someSubj.getMarkString(), "PASSED");
        Assertions.assertFalse(recordBook.hasRedDiploma());
        recordBook.setQualifyingMark(5);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> recordBook.setQualifyingMark(-5));
        Assertions.assertEquals(5, recordBook.getQualifyingMark());
        Assertions.assertTrue(recordBook.hasRedDiploma());
        someSubj.setMark(MarkType.FAILED);
        Assertions.assertEquals(someSubj.getMarkString(), "FAILED");
        Assertions.assertFalse(recordBook.hasRedDiploma());
        Assertions.assertFalse(recordBook.hasHighScholarship());
        someSubj.setMark(MarkType.SATISFACTORY);
        Assertions.assertEquals(someSubj.getMarkString(), "3");
        //System.out.println(recordBook.getSemesters().get(0).tableInfo());
        System.out.println(TablePrinter.recordBookInfo(recordBook));
        //System.out.println(TablePrinter.tableInfoWithBorder(recordBook.getSemesters().get(0)));
        semesters.get(0).toString();
        recordBook.setSemesters(List.of());
        Assertions.assertEquals(recordBook.getAverage(), 0);

    }

    @Test
    void someParserTests() throws ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject object = (JSONObject) jsonParser
                .parse("{\n"
                        + "          \"Mark\": \"-5\",\n"
                        + "          \"Subject\": \"\\u0412\\u0432\\u0435\\u0434\\u0435\\u043d\\u0438\\u0435 "
                        + "\\u0432 \\u0434\\u0438\\u0441\\u043a\\u0440\\u0435\\u0442\\u043d\\u0443\\u044e "
                        + "\\u043c\\u0430\\u0442\\u0435\\u043c\\u0430\\u0442\\u0438\\u043a\\u0443 \\u0438 "
                        + "\\u043c\\u0430\\u0442\\u0435\\u043c\\u0430\\u0442"
                        + "\\u0438\\u0447\\u0435\\u0441\\u043a\\u0443\\u044e "
                        + "\\u043b\\u043e\\u0433\\u0438\\u043a\\u0443\",\n"
                        + "          \"Competencies\": [\n"
                        + "            \"\\u041e\\u041f\\u041a-1\"\n"
                        + "          ],\n"
                        + "          \"Date\": \"18.01.2022\",\n"
                        + "          \"Attestation form\": \"\\u042d\\u043a\\u0437\\u0430\\u043c\\u0435\\u043d\",\n"
                        + "          \"Teachers\": [\n"
                        + "            \"\\u0412\\u043b\\u0430\\u0441\\u043e\\u0432 "
                        + "\\u0414\\u043c\\u0438\\u0442\\u0440\\u0438\\u0439 "
                        + "\\u042e\\u0440\\u044c\\u0435\\u0432\\u0438\\u0447\"\n"
                        + "          ]\n"
                        + "        }");
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                ParserJsonStudentsData.subjectParse(object));
    }

    @Test
    void someSubjectTests() {
        Subject subject = new Subject("C",
                List.of("OPK-12"), "12.12.2012",
                "test", null, MarkType.PASSED);
        Assertions.assertTrue(subject.getTeachers().isEmpty());
        Assertions.assertTrue(subject.addTeacher("Gatilov Stepan Urievich"));
        Assertions.assertEquals("PASSED", subject.getMarkString());
        subject.setMark(MarkType.FAILED);
        Assertions.assertEquals("FAILED", subject.getMarkString());
        Assertions.assertTrue(subject.toString().contains("FAILED"));
    }

    @Test
    void someMarkTypeTest() {
        MarkType markType = MarkType.createMark("2");
        Assertions.assertEquals(2, markType.rawMark());
        Assertions.assertEquals("2", markType.toString());
    }
}
