package nsu.fit.tsukanov.entity.tasks;

import groovy.lang.Closure;
import lombok.Data;
import nsu.fit.tsukanov.dsl.Delegator;
import nsu.fit.tsukanov.entity.common.GeneralConfig;

import java.util.ArrayList;
import java.util.List;

@Data
public class TaskConfig {
    List<Task> tasks = new ArrayList<>();
    Double taskScore;
    private String branchPattern = "task-$1-$2-$3";
    private String folderPattern = "Task_$1_$2_$3";

    public TaskConfig(GeneralConfig generalConfig) {
        taskScore = generalConfig.getEvaluation().getTaskScore();
    }

    public void tasks(Closure<?> closure) {
        Delegator.groovyDelegate(this, closure);
    }

    public void createTask(String name, Integer fst, Integer snd,
                           Integer thd) {
        String branchName = branchPattern
                .replace("$1", fst.toString())
                .replace("$2", snd.toString())
                .replace("$3", thd.toString());
        String folderName = folderPattern
                .replace("$1", fst.toString())
                .replace("$2", snd.toString())
                .replace("$3", thd.toString());
        task(name, folderName, branchName);
    }

    public void createTask(String name, Integer fst, Integer snd,
                           Integer thd, Closure<?> closure) {
        String branchName = branchPattern
                .replace("$1", fst.toString())
                .replace("$2", snd.toString())
                .replace("$3", thd.toString());
        String folderName = folderPattern
                .replace("$1", fst.toString())
                .replace("$2", snd.toString())
                .replace("$3", thd.toString());
        task(name, folderName, branchName, closure);
    }

    public void task(String name, String folder, String branch) {
        Task task = new Task(name, folder, branch, taskScore);
        tasks.add(task);
    }

    public void task(String name, String folder,
                     String branch, Closure<?> closure) {
        Task task = new Task(name, folder, branch);
        Delegator.groovyDelegate(task, closure);
        tasks.add(task);
    }
}
