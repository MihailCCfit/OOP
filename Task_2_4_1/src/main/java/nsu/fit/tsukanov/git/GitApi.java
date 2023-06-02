package nsu.fit.tsukanov.git;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;

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

}