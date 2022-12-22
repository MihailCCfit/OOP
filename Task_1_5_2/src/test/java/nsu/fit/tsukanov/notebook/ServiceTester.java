package nsu.fit.tsukanov.notebook;

import nsu.fit.tsukanov.notebook.core.Configuration;
import nsu.fit.tsukanov.notebook.core.Note;
import nsu.fit.tsukanov.notebook.service.NoteBookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class ServiceTester {
    @Test
    void simple() {
        Date date = new Date();
        NoteBookService service = null;
        try {
            service = new NoteBookService();
            service.clear();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Note note = new Note("Note1", new Date(), "Note1 text hello");
        service.addNote(note);
        try {
            service.saveBook();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(service.getNotes().toString().contains("Note1"));
        Assertions.assertTrue(
                service.getAllNoteNamesFiltered(date, new Date(), List.of("Note"))
                        .toString().contains("Note1"));
        Assertions.assertSame(service.getNote("Note1").text(), note.text());
        service.removeNote("Note1");
        Assertions.assertFalse(service.getNotes().toString().contains("Note1"));
        Assertions.assertFalse(service.toString().isEmpty());
        var noteNames = service
                .getAllNoteNamesFiltered(null, null, List.of("Note"));
        Assertions.assertFalse(noteNames.toString().contains("Note"));
        try {
//            service.removeThisBook();
            service = new NoteBookService("NewBook");
            service.clear();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        service.addNote(note);
        try {
            service.saveBook();

            service = new NoteBookService("NewBook");
            Assertions.assertTrue(service.getNotes().toString().contains("Note1"));
            Assertions.assertTrue(service.getAllNoteNames().toString().contains("Note1"));
            Assertions.assertTrue(service.removeThisBook());
            service = new NoteBookService("NewBook");
            Assertions.assertFalse(service.getNotes().toString().contains("Note1"));
//            service.removeThisBook();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertFalse(Configuration.noteBookPath().isEmpty());
        Assertions.assertFalse(Configuration.configurationPath().isEmpty());

    }
}
