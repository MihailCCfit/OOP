package nsu.fit.tsukanov.entity;

import groovy.lang.Closure;
import lombok.Data;
import nsu.fit.tsukanov.dsl.Delegator;

@Data
public class Group {
    String groupName;
    Student student;
//    List<Student> students = new ArrayList<>();

//    public Group(String groupName, Collection<Student> students) {
//        this.groupName = groupName;
//        this.students.addAll(students);
//    }


    public void student(Closure<?> closure) {
        student = new Student();
        Delegator.groovyDelegate(student, closure);
    }

}
