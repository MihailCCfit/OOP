package nsu.fit.tsukanov.model;

import nsu.fit.tsukanov.model.dsl.GroovyParser;
import nsu.fit.tsukanov.model.entity.common.GeneralConfig;
import nsu.fit.tsukanov.model.entity.fixes.FixConfig;
import nsu.fit.tsukanov.model.entity.fixes.StudentInformation;
import nsu.fit.tsukanov.model.entity.group.GroupConfig;
import nsu.fit.tsukanov.model.entity.tasks.TaskConfig;
import nsu.fit.tsukanov.model.evaluator.Assessment;
import nsu.fit.tsukanov.model.evaluator.StudentEvaluator;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GroovyModel {
    GroovyParser groovyParser = new GroovyParser();
    GeneralConfig generalConfig;
    GroupConfig group;
    TaskConfig taskConfig;
    Map<String, StudentInformation> studentInformationMap;
    FixConfig fixes;

    File generalDir;

    public GroovyModel(File scriptsDir, File generalDir) {
        this.generalDir = generalDir;
        String scriptPath = scriptsDir.getPath() + "/";
        generalConfig = groovyParser.readGeneral(scriptPath + "general.groovy");
        taskConfig = groovyParser.readTasks(generalConfig, scriptPath + "tasks.groovy");
        group = groovyParser.readGroup(generalConfig, scriptPath + "group21214.groovy");
        fixes = groovyParser.readFixes(studentInformationMap = GroovyParser.getStudentInformationMap(group, taskConfig),
                scriptPath + "fixes.groovy");
    }

    public Map<String, Map<String, Assessment>> evaluateAll() {
        Map<String, Map<String, Assessment>> persons = new HashMap<>();
        studentInformationMap.forEach((name, studentInfo) -> {
            persons.put(name, evaluatePerson(studentInfo));
        });

        return persons;
    }

    public Map<String, Assessment> evaluatePerson(StudentInformation studentInfo) {
        try {
            try (StudentEvaluator studentEvaluator = new StudentEvaluator(studentInfo, generalDir,
                    generalConfig)) {
                return studentEvaluator.evaluateTask(taskConfig.getTasks());
            }
        } catch (GitAPIException e) {
            System.err.println(studentInfo.getStudentConfig().getGitName() + ": " + e);
        } catch (IOException e) {
            System.err.println(studentInfo.getStudentConfig().getGitName() + ": " + e);
        } catch (Exception e) {
            System.err.println(studentInfo.getStudentConfig().getGitName() + ": " + e);
        }
        Map<String, Assessment> assessmentMap = new HashMap<>();
        taskConfig.getTasks().forEach((task -> {
            assessmentMap.put(task.id(), Assessment.builder().build());
        }));
        return assessmentMap;
    }

    public Map<String, Assessment> evaluatePerson(String gitName) {
        return evaluatePerson(studentInformationMap.get(gitName));
    }
}
