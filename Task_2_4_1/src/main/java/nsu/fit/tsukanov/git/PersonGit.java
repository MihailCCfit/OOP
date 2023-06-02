package nsu.fit.tsukanov.git;

import nsu.fit.tsukanov.entity.common.GitConfig;
import nsu.fit.tsukanov.entity.fixes.StudentInformation;
import nsu.fit.tsukanov.entity.group.StudentConfig;
import nsu.fit.tsukanov.entity.tasks.Task;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PersonGit {
    Git git;
    StudentInformation studentInfo;
    StudentConfig studentConfig;
    GitConfig gitConfig;
    File workingDir;

    public PersonGit(GitConfig gitConfig, StudentInformation studentInfo,
                     File workingDir) throws IOException, GitAPIException {
        this.gitConfig = gitConfig;
        this.studentInfo = studentInfo;
        this.studentConfig = studentInfo.getStudentConfig();
        this.workingDir = workingDir;
        initFolder();
    }

    public File switchTaskIfNotExists(Task task) throws GitAPIException, IOException {
        try {
            return tryFile(task);
        } catch (IOException e) {
            switchTaskBranch(task);
            return tryFile(task);
        }
    }

    private File tryFile(Task task) throws IOException {
        File taskDir = new File(workingDir, task.getFolder());
        if (taskDir.exists()) {
            return taskDir;
        }
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
        throw new IOException("There is no folder for " + studentConfig.getGitName() + ":" + taskDir);

    }

    public void switchTaskBranch(Task task) throws GitAPIException {
        String taskBranch = task.getBranch();
        String branchByAlias = studentInfo.getBranchRename().get(task.id());
        RefNotFoundException refNotFoundException = null;
        if (branchByAlias != null) {
            try {
                switchBranch("origin/" + branchByAlias);
                return;
            } catch (RefNotFoundException e) {
                refNotFoundException = e;
                System.err.println("Wrong branch alias for " + studentConfig.getGitName() + ": " + branchByAlias);
            }
        }

        List<Integer> numbers;
        if ((numbers = task.getNumbers()) != null && numbers.size() == 3) {
            String branchByPattern = studentInfo.getBranchPattern().replace("$1", numbers.get(0).toString())
                    .replace("$2", numbers.get(1).toString())
                    .replace("$3", numbers.get(2).toString());
            try {
                switchBranch("origin/" + branchByPattern);
                return;
            } catch (RefNotFoundException e) {
                System.err.println("Wrong branch pattern for " + studentConfig.getGitName() + ": "
                        + studentInfo.getBranchPattern());
                refNotFoundException = e;
            }
        }
        try {
            switchBranch("origin/" + taskBranch);
            return;
        } catch (RefNotFoundException e) {
            System.err.println("Wrong branch by default taskBranch for " + studentConfig.getGitName() + ": "
                    + taskBranch);
            refNotFoundException = e;
        }

        throw refNotFoundException;
    }

    private void switchBranch(String branchName) throws GitAPIException {
        git.checkout()
                .setName(branchName)
                .setForced(true)
                .call();
    }


    private void initFolder() throws IOException, GitAPIException {
        if (workingDir.exists()) {
            git = Git.open(workingDir);

            try {
                switchBranch("origin/" + studentConfig.getDefaultBranch());
            } catch (RefAlreadyExistsException e) {
                switchBranch(studentConfig.getDefaultBranch());
            }
        } else {
            String URI = gitConfig.getRepoLinkPrefix() + studentConfig.getGitName()
                    + "/" + studentConfig.getRepository();
            git = Git.cloneRepository()
                    .setDirectory(workingDir)
                    .setURI(URI)
                    .setCloneAllBranches(true)
                    .call();
        }

    }
}
