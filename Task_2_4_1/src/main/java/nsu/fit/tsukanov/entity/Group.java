package nsu.fit.tsukanov.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class Group {
    String groupName;
    List<Student> students = new ArrayList<>();

    public Group(String groupName, Collection<Student> students) {
        this.groupName = groupName;
        this.students.addAll(students);
    }

}
