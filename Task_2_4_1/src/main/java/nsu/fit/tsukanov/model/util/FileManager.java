package nsu.fit.tsukanov.model.util;

import nsu.fit.tsukanov.model.entity.fixes.StudentInformation;
import nsu.fit.tsukanov.model.entity.tasks.Task;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileManager {
    public static File getTaskFolder(Task task, StudentInformation studentInfo, File workingDir) throws IOException {
        File taskDir = new File(workingDir, task.getFolder());

        String dir = studentInfo.getFolderRename().get(task.id());
        if (dir != null) {
            taskDir = new File(workingDir, dir);
            if (taskDir.exists()) {
                return taskDir;
            }
        }
        List<Integer> numbers = task.getNumbers();
        if (numbers != null && numbers.size() == 3) {
            dir = studentInfo.getFolderPattern()
                    .replace("$1", numbers.get(0).toString())
                    .replace("$2", numbers.get(1).toString())
                    .replace("$3", numbers.get(2).toString());
            taskDir = new File(workingDir, dir);
            if (taskDir.exists()) {
                return taskDir;
            }
        }
        if (taskDir.exists()) {
            return taskDir;
        }
        throw new IOException("There is no folder for " + studentInfo.getStudentConfig().getGitName() + ":" + taskDir);

    }
}
