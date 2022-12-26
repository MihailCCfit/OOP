package nsu.fit.tsukanov.notebook.controller;

import nsu.fit.tsukanov.notebook.core.Configuration;
import nsu.fit.tsukanov.notebook.core.Note;
import nsu.fit.tsukanov.notebook.service.NoteBookService;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.util.Date;
import java.util.concurrent.Callable;

@Command(name = "add",
        version = "add " + Configuration.version,
        description = "Add note to the current noteBook.")
public class NoteAdd implements Callable<Integer> {
    @Parameters(index = "0")
    String noteName = null;
    @Parameters(index = "1")
    String noteText = null;
    @CommandLine.Option(names = {"-date", "-d"}, arity = "0..1")
    Date date = null;

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Integer call() throws Exception {
        NoteBookService service = new NoteBookService(Configuration.bookName, true);
        service.addNote(new Note(noteName, date != null ? date : new Date(), noteText));
        service.saveBook();
        System.out.println("Note {" + noteName + "} successfully saved into the " + Configuration.bookName);
        return 0;
    }
}
