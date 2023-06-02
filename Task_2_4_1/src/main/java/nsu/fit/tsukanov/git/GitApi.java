package nsu.fit.tsukanov.git;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;

public class GitApi {
    public static Git cloneRepository(File directory, String URI) {
        try {
            return Git.cloneRepository()
                    .setDirectory(directory)
                    .setURI(URI)
                    .setCloneAllBranches(true)
                    .call();
        } catch (GitAPIException e) {
            System.err.println("gfqs");
            throw new RuntimeException(e);
        }
    }
}