package nsu.fit.tsukanov.notebook.controller;

import nsu.fit.tsukanov.notebook.core.Configuration;
import nsu.fit.tsukanov.notebook.service.NoteBookService;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

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

    @Command(header = "book remove", description = "Remove this noteBook")
    void bookRemove(@Option(names = {"-brm", "-bookremove"}) String bookName) {
        if (bookName == null){

            return;
        }
        NoteBookService service;
        try {
            service = new NoteBookService(bookName);
            var wasDeleted = service.removeThisBook();
            System.out.println(wasDeleted?
                    "NoteBook ("+bookName + ") was deleted"
                    : "Note Book (" + bookName + ") wasn't deleted");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    @Override
    public Integer call() {
        return 0;
    }
}
