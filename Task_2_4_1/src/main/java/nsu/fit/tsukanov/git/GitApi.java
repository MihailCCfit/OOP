package nsu.fit.tsukanov.git;

import org.eclipse.jgit.api.CreateBranchCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;

public class GitApi {
    private String githubLinkPrefix = "https://github.com/";
    private String githubLinkPostfix = ".git";
    private File saveClonedDirectory = new File(
            "./save");

    public GitApi(String githubLinkPrefix, String githubLinkPostfix, File saveClonedDirectory) {
        this.githubLinkPrefix = githubLinkPrefix;
        this.githubLinkPostfix = githubLinkPostfix;
        this.saveClonedDirectory = saveClonedDirectory;
    }

    public GitApi() {
    }

    public void cloneRepository(String repositoryName) {
        saveClonedDirectory.mkdirs();
        try {
            Git git = Git.cloneRepository()
                    .setURI(githubLinkPrefix + repositoryName + githubLinkPostfix)
                    .setDirectory(saveClonedDirectory)
                    .setCloneAllBranches(true)
                    .call();
        } catch (GitAPIException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isOnBranch(Git git, String branchName) {
        try {
            return git.getRepository()
                    .getFullBranch()
                    .equals(branchName);
        } catch (IOException e) {
            return false;
        }
    }

    public static void checkout(Git git, String branchName) throws GitAPIException {
        git.checkout()
                .setName(branchName)
                .setUpstreamMode(CreateBranchCommand.SetupUpstreamMode.TRACK)
                .setStartPoint("origin/" + branchName)
                .call();
    }
}