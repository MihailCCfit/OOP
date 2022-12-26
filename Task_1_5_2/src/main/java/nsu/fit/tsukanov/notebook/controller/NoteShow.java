package nsu.fit.tsukanov.notebook.controller;

import nsu.fit.tsukanov.notebook.core.Configuration;
import nsu.fit.tsukanov.notebook.core.Note;
import nsu.fit.tsukanov.notebook.service.FilterBuilder;
import nsu.fit.tsukanov.notebook.service.NoteBookService;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

@Command(name = "show", aliases = {"sh"}, mixinStandardHelpOptions = true,
        version = "show " + Configuration.version,
        description = "show notes of the current noteBook.")
public class NoteShow implements Callable<Integer> {
    @Option(names = "-from",
            arity = "1",
            description = "Date from to sort notes")
    Date from = null;

    @Option(names = "-to",
            arity = "1",
            description = "Date to to sort notes")
    Date to = null;

    @Parameters(arity = "0..*")
    List<String> words = null;


    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Integer call() {
        String bkName = Configuration.bookName;
        NoteBookService noteBookService = null;
        try {
            noteBookService = new NoteBookService(bkName);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return 1;
        }
        if (!noteBookService.wasOpened()) {
            System.out.println("There is no book with this name:  " + Configuration.bookName);
            return 0;
        }
        var notes = noteBookService.getFilteredNotes(from, to, words);
        System.out.println(FilterBuilder.showFilter(from, to, words));
        System.out.println("\n" + "#".repeat(40)+ "\n"
                + notes.stream().map(Note::toString).reduce((x, y) -> x + "\n" + "-".repeat(40) + "\n" + y).get()
                + "\n" + "#".repeat(40)+ "\n");
        return 0;
    }
}
