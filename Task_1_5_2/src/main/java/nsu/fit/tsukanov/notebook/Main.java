package nsu.fit.tsukanov.notebook;

import nsu.fit.tsukanov.notebook.controller.NoteBookController;
import picocli.CommandLine;

public class Main {
    public static void main(String[] args) {
        int exitCode = new CommandLine(new NoteBookController()).execute(args);
        System.exit(exitCode);
    }
}
