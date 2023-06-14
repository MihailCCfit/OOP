package nsu.fit.tsukanov.model.evaluator;

import nsu.fit.tsukanov.model.entity.common.EvaluationConfig;
import nsu.fit.tsukanov.model.entity.fixes.StudentInformation;
import nsu.fit.tsukanov.model.entity.tasks.Task;
import nsu.fit.tsukanov.model.evaluator.xml.JacocoReportParser;
import nsu.fit.tsukanov.model.gradle.GradleTool;
import nsu.fit.tsukanov.model.util.FileManager;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 * It's class evaluate only one task (only one folder) with gradle
 */
public class TaskEvaluator {
    static private String moduleName;

    private TaskEvaluator() {
    }

    public static Assessment evaluate(GradleTool gradleTool, Task task, StudentInformation studentInfo,
                                      EvaluationConfig evaluationConfig)
            throws IOException {
        File moduleDir = FileManager.getTaskFolder(task, studentInfo, gradleTool.getProjectFolder());
        File workingDir = moduleDir.getParentFile();
        moduleName = getModuleName(moduleDir, workingDir);
        boolean runTests = task.getRunTests();
        Assessment.AssessmentBuilder assessmentBuilder = Assessment.builder();


        var builder = GradleTool.TaskList.builder()
                .taskPair(new GradleTool.TaskPair(moduleTaskName("clean"),
                        () -> System.out.println("clean")))
                .taskPair(new GradleTool.TaskPair(moduleTaskName("build"),
                        () -> assessmentBuilder.buildMark(evaluationConfig.getBuildScore())))
                .taskPair(new GradleTool.TaskPair(moduleTaskName("javadoc"),
                        () -> assessmentBuilder.docsMark(evaluationConfig.getDocScore())));

        if (runTests) {
            builder.taskPair(new GradleTool.TaskPair(moduleTaskName("jacocoTestReport"),
                    () -> assessmentBuilder.testMark(checkJacoco(evaluationConfig, moduleDir))));
        } else {
            assessmentBuilder.buildMark(evaluationConfig.getJacocoPercentage());
        }
        try {
            FileManager.addCheckStyle(moduleDir, new File("google_checks.xml"));
            builder.taskPair(new GradleTool.TaskPair(moduleTaskName("checkStyleMain"), () -> {
                assessmentBuilder.styleScores(evaluationConfig.getCheckStyleScore());
            }));

        } catch (IOException e) {
            System.err.println("checkstyleProblem: " + e);
            assessmentBuilder.styleScores(0.0);
        }


        assessmentBuilder.extraScores(studentInfo.getExtraScore().get(task.id()));
        var taskList = builder.build();
        gradleTool.runTask(taskList.taskPairs);

        return assessmentBuilder.build();
    }

    private static Double checkJacoco(EvaluationConfig evaluationConfig, File moduleDir) {
        File jacocoXmlFile = new File(moduleDir, "build/reports/jacoco/test/jacocoTestReport.xml");
        try {

            var jacocoParser = JacocoReportParser.parse(jacocoXmlFile);
            Optional<JacocoReportParser.Counter> maybeResults = jacocoParser
                    .getCounterByType(JacocoReportParser.CounterType.INSTRUCTION);
            if (maybeResults.isPresent()) {
                JacocoReportParser.Counter results = maybeResults.get();
                System.out.println(results);
                double coveredPercentage = 100 * results.covered() / ((double) results.covered() + results.missed());
                return coveredPercentage >= evaluationConfig.getJacocoPercentage() ?
                        evaluationConfig.getJacocoScore() : 0;
            }
            return 0.0;
        } catch (IOException e) {
            return 0.0;
        }

    }

    private static String moduleTaskName(String taskName) {
        return moduleName + ":" + taskName;
    }

    private static String getModuleName(File moduleDir, File workingDir) {
        String moduleName = moduleDir.getAbsolutePath().replace(workingDir.getAbsolutePath(), "");
        moduleName = moduleName.replace("\\", "").replace("/", "");
        return moduleName;
    }

}
