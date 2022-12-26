package nsu.fit.tsukanov.notebook.controller;

import nsu.fit.tsukanov.notebook.core.Configuration;
import nsu.fit.tsukanov.notebook.service.NoteBookService;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Remove note from the current noteBook.
 */
@Command(name = "remove", aliases = {"rm"},
        version = "remove " + Configuration.version,
        description = "Remove note from the current noteBook.")
public class NoteRemove implements Callable<Integer> {
    @Parameters(index = "0..*", description = "the name of note")
    List<String> noteNames = null;

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Integer call() throws Exception {
        NoteBookService bookService = new NoteBookService(Configuration.bookName);
        if (noteNames == null) {
            System.err.println("There is no names (null) for removing. Try to type name for removing");
            return 1;
        }
        if (!bookService.wasOpened()){
            System.out.println("There is no noteBook with this name: " + Configuration.bookName);
            return 1;
        }
        List<String> found = new ArrayList<>();
        List<String> notFound = new ArrayList<>();
        for (String noteName : noteNames) {
            var res = bookService.removeNote(noteName);
            bookService.saveBook();
            if (res == null) {
                notFound.add(noteName);

            } else {
                found.add(noteName);

            }
        }
        System.out.println("There are no notes with this " + notFound + " names in the "
                + Configuration.bookName);
        System.out.println("Notes " + found + " was successfully deleted from the "
                + Configuration.bookName);
        return 0;
    }
}
