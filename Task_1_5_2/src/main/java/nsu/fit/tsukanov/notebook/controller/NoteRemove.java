package nsu.fit.tsukanov.notebook.controller;

import nsu.fit.tsukanov.notebook.core.Configuration;
import nsu.fit.tsukanov.notebook.service.NoteBookService;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.util.concurrent.Callable;

@Command(name = "remove", aliases = {"rm"},
        version = "remove " + Configuration.version,
        description = "Remove note from the current noteBook.")
public class NoteRemove implements Callable<Integer> {
    @Parameters(index = "0", description = "the name of note")
    String noteName = null;

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Integer call() throws Exception {
        NoteBookService bookService = new NoteBookService(Configuration.bookName);
        if (noteName == null) {
            System.err.println("There is no name (null) for removing. Try to type name for removing");
            return 1;
        }
        if (!bookService.wasOpened()){
            System.out.println("There is no noteBook with this name: " + Configuration.bookName);
        }

        var res = bookService.removeNote(noteName);
        bookService.saveBook();
        if (res == null) {
            System.out.println("There is no note with this (" + noteName + ") name in the "
                    + Configuration.bookName);
        } else {
            System.out.println("Note (" + noteName + ") was successfully deleted from the "
                    + Configuration.bookName);
        }

        return 0;
    }
}
