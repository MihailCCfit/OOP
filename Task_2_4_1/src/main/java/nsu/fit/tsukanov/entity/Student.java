package nsu.fit.tsukanov.entity;

import lombok.Data;

@Data
public class Student {
    String name;
    String githubName;
    String repoUrl;
    String defaultBranch;
}
