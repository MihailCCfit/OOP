package nsu.fit.tsukanov.notebook.controller;

import nsu.fit.tsukanov.notebook.core.Configuration;
import nsu.fit.tsukanov.notebook.service.NoteBookService;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.IOException;
import java.util.concurrent.Callable;

@Command(name = "notebook", mixinStandardHelpOptions = true,
        version = "notebook " + Configuration.version,
        subcommands = {NoteAdd.class, NoteRemove.class, NoteShow.class},
        description = "Save, show notes.")
public class NoteBookController implements Callable<Integer> {

    @Option(names = {"-bn", "--bookname"},
            arity = "1",
            description = "Nothing if basic, some - for specified",
            scope = CommandLine.ScopeType.INHERIT)

    private void setBookName(String bookName) {
        if (bookName != null) {
            Configuration.bookName = bookName;
        }
    }

    @Command(name = "bookRemove",header = "book remove", description = "Remove this noteBook")
    void bookRemove(@Parameters(index = "0", arity = "0..1") String bookName) {
        NoteBookService service;
        if (bookName == null){
            bookName = Configuration.BasicNoteBookName;
        }
        try {
            service = new NoteBookService(bookName);
            var wasDeleted = service.removeThisBook();
            System.out.println(wasDeleted?
                    "NoteBook ("+bookName + ") was deleted"
                    : "Note Book (" + bookName + ") wasn't deleted");
        } catch (IOException e) {
            System.out.println("There is no book with this name: " + bookName);
        }
        catch (IllegalStateException e){
            System.out.println("There is no noteBook with this name: "+ bookName);
        }

    }

    @Override
    public Integer call() {
        return 0;
    }
}
