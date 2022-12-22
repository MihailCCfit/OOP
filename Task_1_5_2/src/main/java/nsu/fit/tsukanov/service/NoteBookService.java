package nsu.fit.tsukanov.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import nsu.fit.tsukanov.notebook.core.Configuration;
import nsu.fit.tsukanov.notebook.core.Note;
import nsu.fit.tsukanov.notebook.core.NoteBook;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class NoteBookService implements NoteBookServiceInterface {
    private NoteBook noteBook;

    public void openBook(String name) throws IOException {
        String noteBookPath = Configuration.noteBookPath(name);
        File noteBookFile = new File(noteBookPath);

        if (noteBookFile.exists()) {
            ObjectMapper mapper = new ObjectMapper();
            noteBook = mapper.readValue(noteBookFile, NoteBook.class);
        } else {
            noteBook = new NoteBook(Configuration.getNoteBookName(name), new TreeMap<>());
            saveBook();
        }
    }

    public static boolean removeBook() {
        return removeBook(null);
    }

    public static boolean removeBook(String name) {
        String noteBookPath = Configuration.noteBookPath(name);
        File noteBookFile = new File(noteBookPath);
        return noteBookFile.delete();
    }

    public boolean removeThisBook() {
        String noteBookPath = Configuration.noteBookPath(noteBook.noteBookName());
        File noteBookFile = new File(noteBookPath);
        return noteBookFile.delete();
    }

    public void saveBook() throws IOException {
        String noteBookPath = Configuration.noteBookPath(noteBook.noteBookName());
        File noteBookFile = new File(noteBookPath);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(noteBookFile, noteBook);
    }

    public NoteBookService() throws IOException {
        this(null);
    }

    public NoteBookService(String noteBookName) throws IOException {
        openBook(noteBookName);
    }

    public List<String> getAllNoteNames() {
        return noteBook.noteTable().keySet().stream().toList();
    }

    public List<String> getAllNoteNamesFiltered(Date from, Date to, List<String> words) {
        return getFilteredNotes(from, to, words).stream().map(Note::name).collect(Collectors.toList());
    }

    public Note getNote(String noteName) {
        return noteBook.noteTable().get(noteName);
    }

    /**
     * Add note to the NoteBook.
     *
     * @param note the note that will be added
     * @return true, if note was added
     */
    @Override
    public Note addNote(Note note) {
        return noteBook.noteTable().put(note.name(), note);
    }

    /**
     * Remove note from the notebook
     *
     * @param noteName the name of note
     * @return null - if there is no Note with this name. Otherwise - return removed note.
     */
    @Override
    public Note removeNote(String noteName) {
        return noteBook.noteTable().remove(noteName);
    }

    /**
     * Return all nodes in this noteBook.
     *
     * @return all notes in this notebook
     */
    @Override
    public List<Note> getNotes() {
        return noteBook.noteTable().values().stream().toList();
    }

    /**
     * Filtering notes.
     *
     * @param from  the date from which records will be filtered
     * @param to    the date until which records will be filtered
     * @param words the words for checking containing them in the headers of notes.
     * @return all filtered notes from this noteBook.
     */
    @Override
    public List<Note> getFilteredNotes(Date from, Date to, List<String> words) {
        FilterBuilder filterBuilder = new FilterBuilder(getNotes())
                .after(from)
                .before(to)
                .containsInHeader(words);
        return filterBuilder.build();
    }

    @Override
    public String toString() {
        return noteBook.toString();
    }
}
