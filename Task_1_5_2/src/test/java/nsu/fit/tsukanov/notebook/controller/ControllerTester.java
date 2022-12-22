package nsu.fit.tsukanov.notebook.controller;

import nsu.fit.tsukanov.notebook.service.NoteBookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class ControllerTester {
    @Test
    void tst() {
        NoteBookController bookController = new NoteBookController();
        bookController.show = true;
        bookController.call();
        bookController.show = false;
        String name = "Name";
        bookController.bookName = name;
        try {
            NoteBookService bookService = new NoteBookService(name);
            bookService.clear();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        bookController.addList = List.of("Note2", "Text of Note2");
        bookController.call();
        bookController.addList = List.of("Note3", "Text of Note3");
        bookController.call();
        try {
            NoteBookService bookService = new NoteBookService(name);

            Assertions.assertTrue(bookService.getAllNoteNames().toString().contains("Note2"));
            Assertions.assertTrue(bookService.getNotes().toString().contains("Text of Note3"));
            bookController.addList = null;
            bookController.rmNote = "Note2";
            bookController.call();
            bookService = new NoteBookService(name);
            Assertions.assertFalse(bookService.getAllNoteNames().toString().contains("Note2"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
