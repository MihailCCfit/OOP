package RecordBook;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Semester {
    private Map<String, Subject> subjectMap;
    private int number;

    Semester(Collection<Subject> subjects, int number){
        this.number = number;
        subjectMap = new HashMap<>();
        for (Subject subject : subjects) {
            subjectMap.put(subject.getSubjectName(), subject);
        }
    }

    public static void main(String[] args) {
        nothing();
    }
    static private void nothing(){
        Subject subject = new Subject("Введение в алгебру и анализ", List.of("ОПК-1"),
                "16.06.2022", "Экзамен",
                List.of("Васкевич Владимир Леонтьевич", "Рудометова Анна Сергеевна", "Шваб Ирина Васильевна"),
                5);
        System.out.println(subject);
        System.out.println(subject.shortResult());
        Subject subject1 = new Subject("Введение в алгебру и анализ", List.of("ОПК-1"), "Экзамен");
        System.out.println(subject1);
        System.out.println(subject1.shortResult());
        Student me = new Student("Tsukanov", "Mikhail", "CCFIT", 21214, "m.tsukanov");
        System.out.println(me);
    }
}
