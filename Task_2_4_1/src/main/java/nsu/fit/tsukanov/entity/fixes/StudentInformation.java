package nsu.fit.tsukanov.entity.fixes;

import lombok.Data;
import nsu.fit.tsukanov.entity.group.StudentConfig;
import nsu.fit.tsukanov.entity.tasks.TaskConfig;

import java.util.HashMap;
import java.util.Map;

@Data
public class StudentInformation {
    StudentConfig studentConfig;
    Map<String, String> branchRename = new HashMap<>();
    Map<String, String> folderRename = new HashMap<>();
    Map<String, Double> extraScore = new HashMap<>();
    String branchPattern;
    String folderPattern;

    public StudentInformation(StudentConfig studentConfig) {
        this.studentConfig = studentConfig;
    }

    public StudentInformation(StudentConfig studentConfig, TaskConfig taskConfig) {
        this(studentConfig);
        branchPattern = taskConfig.getBranchPattern();
        folderPattern = taskConfig.getFolderPattern();
        taskConfig.getTasks().forEach(task -> {
            extraScore.put(task.id(), 0.0);
        });
    }
}
