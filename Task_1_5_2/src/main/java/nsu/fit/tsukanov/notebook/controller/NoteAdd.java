package nsu.fit.tsukanov.notebook.controller;

import nsu.fit.tsukanov.notebook.core.Configuration;
import nsu.fit.tsukanov.notebook.core.Note;
import nsu.fit.tsukanov.notebook.service.NoteBookService;
import picocli.CommandLine.Option;
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
    @Parameters(index = "1", arity = "0..1")
    String noteText = "";
    @Option(names = {"-date", "-d"}, arity = "0..1")
    Date date = null;
    @Option(names = {"-create", "-c"}, arity = "0")
    Boolean createNew = false;

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Integer call() throws Exception {
        NoteBookService service = new NoteBookService(Configuration.bookName, createNew);
        if (!service.wasOpened()){
            System.err.println("There is no book with this name + {" + Configuration.bookName
                    + "}, try use with [-c] or [-create] keys");
            return 1;
        }
        service.addNote(new Note(noteName, date != null ? date : new Date(), noteText));
        service.saveBook();
        System.out.println("Note {" + noteName + "} successfully saved into the " + Configuration.bookName);
        return 0;
    }
}
