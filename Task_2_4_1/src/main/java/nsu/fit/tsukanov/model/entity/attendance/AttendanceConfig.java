package nsu.fit.tsukanov.model.entity.attendance;

import lombok.Data;
import nsu.fit.tsukanov.model.entity.fixes.StudentInformation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class AttendanceConfig {

    private final Map<String, StudentInformation> studentInformationMap;
    private final List<Lesson> lessons;

    private DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public AttendanceConfig(Map<String, StudentInformation> studentInformationMap, List<Lesson> lessons) {
        this.studentInformationMap = studentInformationMap;
        this.lessons = lessons;
    }

    public void attended(String student, String dateString) {
        Date date;
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            System.err.println(e);
            return;
        }
        Lesson lesson = new Lesson(date);
        if (lessons.contains(lesson)) {
            studentInformationMap.get(student).getStudentAttendance().add(lesson);
        }
    }

    public void attended(String student, String dateStartString, String dateEndString) {


    }
}
