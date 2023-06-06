package nsu.fit.tsukanov;

import nsu.fit.tsukanov.model.dsl.GroovyParser;
import nsu.fit.tsukanov.model.entity.common.GeneralConfig;
import nsu.fit.tsukanov.model.entity.fixes.FixConfig;
import nsu.fit.tsukanov.model.entity.fixes.StudentInformation;
import nsu.fit.tsukanov.model.entity.group.GroupConfig;
import nsu.fit.tsukanov.model.entity.tasks.TaskConfig;
import nsu.fit.tsukanov.model.evaluator.StudentEvaluator;
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

        File generalDir = new File("newFolder");

        studentInformationMap = fixes.getInformationMap();


        studentInformationMap.forEach((name, studentInfo) -> {
            try {
                System.out.println(studentInfo);
                StudentEvaluator studentEvaluator = new StudentEvaluator(studentInfo, generalDir,
                        generalConfig);
                System.out.println(studentEvaluator.evaluateTask(taskConfig.getTasks()));
            } catch (GitAPIException e) {
                System.err.println(name + ": " + e);
            } catch (IOException e) {
                System.err.println(name + ": " + e);
            }
        });


    }
}