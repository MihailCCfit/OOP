package nsu.fit.tsukanov;

import nsu.fit.tsukanov.dsl.GroovyParser;
import nsu.fit.tsukanov.entity.common.GeneralConfig;
import nsu.fit.tsukanov.entity.common.GitConfig;
import nsu.fit.tsukanov.entity.fixes.FixConfig;
import nsu.fit.tsukanov.entity.fixes.StudentInformation;
import nsu.fit.tsukanov.entity.group.GroupConfig;
import nsu.fit.tsukanov.entity.tasks.Task;
import nsu.fit.tsukanov.entity.tasks.TaskConfig;
import nsu.fit.tsukanov.git.PersonGit;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
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

//        StudentInformation meInfo = fixes.getInformationMap().get("MihailCCfit");
//        StudentConfig me = meInfo.getStudentConfig();

        GitConfig gitConfig = generalConfig.getGit();
        File generalDir = new File("newFolder");
        Map<String, PersonGit> personalGits = new HashMap<>();
        fixes.getInformationMap().forEach(((name, studentInformation) -> {
            try {
                File dir = new File(generalDir, studentInformation.getStudentConfig().getGitName());
                PersonGit personGit = new PersonGit(gitConfig, studentInformation, dir);
                personalGits.put(name, personGit);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            } catch (GitAPIException e) {
                System.err.println(e.getMessage());
            }
        }));

        Task task = taskConfig.getTasks().get(taskConfig.getTasks().size() - 1);
        System.out.println(task);
        personalGits.forEach(((s, personGit) -> {
            try {
                personGit.switchTaskIfNotExists(task);
            } catch (GitAPIException e) {
                System.err.println(e);
            } catch (IOException e) {
                System.err.println("There is no file for task " + task.id() + ", " + e);
            }
        }));


    }
}