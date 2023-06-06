package nsu.fit.tsukanov;

import nsu.fit.tsukanov.dsl.GroovyParser;
import nsu.fit.tsukanov.entity.common.GeneralConfig;
import nsu.fit.tsukanov.entity.common.GitConfig;
import nsu.fit.tsukanov.entity.fixes.FixConfig;
import nsu.fit.tsukanov.entity.fixes.StudentInformation;
import nsu.fit.tsukanov.entity.group.GroupConfig;
import nsu.fit.tsukanov.entity.tasks.Task;
import nsu.fit.tsukanov.entity.tasks.TaskConfig;
import nsu.fit.tsukanov.evaluator.StudentEvaluator;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class GroovyParasha {

    public static void main(String[] args) {
        GroovyParser groovyParser = new GroovyParser();
        GeneralConfig generalConfig = groovyParser.readGeneral("scripts/general.groovy");
        GroupConfig group = groovyParser.readGroup(generalConfig, "scripts/group21214.groovy");
        TaskConfig taskConfig = groovyParser.readTasks(generalConfig, "scripts/tasks.groovy");
        Map<String, StudentInformation> studentInformationMap =
                GroovyParser.getStudentInformationMap(group, taskConfig);
        FixConfig fixes = groovyParser.readFixes(studentInformationMap, "scripts/fixes.groovy");

        GitConfig gitConfig = generalConfig.getGit();
        File generalDir = new File("newFolder");

        studentInformationMap = fixes.getInformationMap();


        Task task = taskConfig.getTasks().get(1);
        studentInformationMap.forEach((name, studentInfo) -> {
            try {
                System.out.println(studentInfo);
                StudentEvaluator studentEvaluator = new StudentEvaluator(studentInfo, generalDir,
                        generalConfig);
                System.out.println(studentEvaluator.evaluateTask(task));
            } catch (GitAPIException e) {
                System.err.println(name + ": " + e);
            } catch (IOException e) {
                System.err.println(name + ": " + e);
            }
        });



    }
}