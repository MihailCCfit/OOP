package nsu.fit.tsukanov.notebook.controller;

import nsu.fit.tsukanov.notebook.core.Configuration;
import nsu.fit.tsukanov.notebook.service.NoteBookService;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * Controls notebook and notes. By the "-bn" you can use different noteBooks.
 */
@Command(name = "smallNoteBook", aliases = {"notebook", "snb", "smallBook"}, mixinStandardHelpOptions = true,
        version = "notebook " + Configuration.version,
        subcommands = {NoteAdd.class, NoteRemove.class, NoteShow.class},
        description = "Save notes by adding, remove notes, show notes filtering by dates\n" +
                "You can use different noteBooks")
public class NoteBookController implements Callable<Integer> {


    /**
     * Set current book.
     *
     * @param bookName the name of which book it needs to open
     */
    @Option(names = {"-bn", "--bookname"},
            arity = "1",
            description = "Nothing if basic, some - for specified",
            scope = CommandLine.ScopeType.INHERIT)

    private void setBookName(String bookName) {
        if (bookName != null) {
            Configuration.bookName = bookName;
        }
    }

    /**
     * Remove noteBook with specified name or default noteBook.
     *
     * @param bookName the name of book for removing
     */
    @Command(name = "bookRemove", header = "book remove", description = "Remove this noteBook")
    void bookRemove(@Parameters(index = "0", arity = "0..1") String bookName) {
        NoteBookService service;
        if (bookName == null) {
            bookName = Configuration.BasicNoteBookName;
        }
        try {
            service = new NoteBookService(bookName);
            service.removeThisBook();
            System.out.println(
                    "NoteBook (" + bookName + ") was deleted");
        } catch (IllegalStateException e) {
            System.out.println("There is no noteBook with this name: " + bookName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Integer call() {
        return 0;
    }
}
