package nsu.fit.tsukanov.entity.tasks;

import lombok.Data;


@Data
public class Task {
    String name;
    /**
     * Module name.
     */
    String folder;
    String branch;
    Double points;
    Boolean runTests = true;

    public Task(String name, String folder, String branch) {
        this.name = name;
        this.folder = folder;
        this.branch = branch;
    }

    public Task(String name, String folder,
                String branch, Double points) {
        this(name, folder, branch);
        this.points = points;
    }

}
