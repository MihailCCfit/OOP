package nsu.fit.tsukanov.notebook.controller;

import nsu.fit.tsukanov.notebook.core.Note;
import nsu.fit.tsukanov.notebook.service.NoteBookService;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

@Command(name = "notebook", mixinStandardHelpOptions = true,
        version = "notebook 1.0",
        description = "Save, show notes.")
public class NoteBookController implements Callable<Integer> {


    @Option(names = "-add",
            arity = "2",
            description = "add not {<name> <text>}")
    List<String> addList = null;

    @Option(names = "-rm",
            arity = "1",
            description = "remove opened notebook")
    String rmNote = null;

    @Option(names = "-show",
            description = "show notes")
    boolean show;

    @Option(names = "-from",
            arity = "1",
            description = "Date from to sort notes")
    Date from = null;

    @Option(names = "-to",
            arity = "1",
            description = "Date to to sort notes")
    Date to = null;

    @Parameters(index = "0..*",
            description = "The arguments for filtering notes that will be showed")
    List<String> words = null;

    @Option(names = {"-bn", "--bookname"},
            arity = "1",
            description = "Nothing if basic, some - for specified")
    String bookName = null;


    // this example implements Callable, so parsing, error handling
    // and handling user requests for usage help or version help
    // can be done with one line of code.
//    public static void main(String... args) {
//        int exitCode = new CommandLine(new NoteBookController()).execute("-bn", "MyBook","-add", "First day", "Hello world!");
//        System.exit(exitCode);
//    }

    @Override
    public Integer call() { // the business logic...
        System.out.println("Start");
        NoteBookService noteBookService = null;
        try {
            noteBookService = new NoteBookService(bookName);
        } catch (IOException e) {
            System.err.println(e);
        }

        if (show) {
            System.out.println(noteBookService.getFilteredNotes(from, to, words));
        }
        if (rmNote != null) {
            Note res = noteBookService.removeNote(rmNote);
            if (res == null) {
                System.out.println("There is no note for remove: {" + rmNote + "}");
            }
            try {
                noteBookService.saveBook();
            } catch (IOException e) {
                System.err.println(e);
            }
        }

        if (addList != null) {
            String name = addList.get(0);
            String text = addList.get(1);
            var prevNote = noteBookService.addNote(new Note(name, new Date(), text));
            try {
                noteBookService.saveBook();
            } catch (IOException e) {
                System.err.println(e);
            }
            System.out.println("Note saved, previous: " + (prevNote == null ? "" : prevNote));
        }
//        System.out.println("End");
        return 0;
    }
}
