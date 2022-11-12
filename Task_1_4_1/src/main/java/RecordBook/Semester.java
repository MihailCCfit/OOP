package RecordBook;

import java.util.List;

public class Semester {
    private Subject[] subjects;

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
    }
}
