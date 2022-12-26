package nsu.fit.tsukanov.notebook.controller;

import nsu.fit.tsukanov.notebook.service.NoteBookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.IOException;
import java.util.List;

public class ControllerTester {
    @Test
    void tst() {
        NoteBookController bookController = new NoteBookController();
        bookController.call();
        CommandLine commandLine = new CommandLine(bookController);
        commandLine.execute("bookRemove");
        commandLine.execute("add", "Header", "Body", "-c");
        commandLine.execute("add", "pizza", "hello world", "-c");
        try {
            NoteBookService service = new NoteBookService();
            Assertions.assertFalse(service.getNotes().isEmpty());
            commandLine.execute("show");
            commandLine.execute("show", "Header", "pizza");
            Assertions.assertTrue(service.getAllNoteNames()
                    .containsAll(List.of("pizza", "Header")));
            commandLine.execute("remove", "Header", "pizza");
            service.reopenBook();
            Assertions.assertTrue(service.getNotes().isEmpty());
            commandLine.execute("bookRemove");
            commandLine.execute("-bn", "NewBook", "add", "-c", "Aboba", "abobus");
            var res = commandLine.execute("-bn", "NotBook", "add", "Aboba");

            Assertions.assertEquals(1, res);
            res = commandLine.execute("-bn", "NotBook", "remove");
            Assertions.assertEquals(1, res);
            res = commandLine.execute("-bn", "NotBook", "remove", "124124");
            Assertions.assertEquals(1, res);
            Assertions.assertEquals(1, res);
            commandLine.execute("-bn", "NotBook", "show");
            commandLine.execute("bookRemove");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
