package nsu.fit.tsukanov.evaluator;

import nsu.fit.tsukanov.entity.common.EvaluationConfig;
import nsu.fit.tsukanov.entity.common.GeneralConfig;
import nsu.fit.tsukanov.entity.fixes.StudentInformation;
import nsu.fit.tsukanov.entity.tasks.Task;
import nsu.fit.tsukanov.git.PersonGit;
import nsu.fit.tsukanov.gradle.GradleTool;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;

public class StudentEvaluator implements AutoCloseable {
    private final StudentInformation studentInformation;
    private final PersonGit personGit;
    private final EvaluationConfig evaluationConfig;
    private final GradleTool gradleTool;

    public StudentEvaluator(StudentInformation studentInformation, File generalDir,
                            GeneralConfig generalConfig) throws GitAPIException, IOException {
        this.studentInformation = studentInformation;
        File studentDir = new File(generalDir, studentInformation.getStudentConfig().getGitName());
        personGit = new PersonGit(generalConfig.getGit(), studentInformation, studentDir);
        evaluationConfig = generalConfig.getEvaluationConfig();
        gradleTool = new GradleTool(studentDir);
    }

    public Assessment evaluateTask(Task task) throws IOException, GitAPIException {
        personGit.switchTaskIfNotExists(task);
        return TaskEvaluator.evaluate(gradleTool, task, studentInformation, evaluationConfig);
    }

    @Override
    public void close() throws Exception {
        gradleTool.close();
        personGit.close();
    }
}
