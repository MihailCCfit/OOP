package nsu.fit.tsukanov.service;

import nsu.fit.tsukanov.notebook.core.Note;

import java.util.Date;
import java.util.List;

public interface NoteBookServiceInterface {
    /**
     * Add note to the NoteBook.
     *
     * @param note the note that will be added
     * @return previous note
     */
    Note addNote(Note note);

    /**
     * Remove note from the notebook
     *
     * @param noteName the name of note
     * @return null - if there is no Note with this name. Otherwise - return removed note.
     */

    Note removeNote(String noteName);

    /**
     * Return all nodes in this noteBook.
     *
     * @return all notes in this notebook
     */
    List<Note> getNotes();

    /**
     * Filtering notes.
     *
     * @param from  the date from which records will be filtered
     * @param to    the date until which records will be filtered
     * @param words the words for checking containing them in the headers of notes.
     * @return all filtered notes from this noteBook.
     */

    List<Note> getFilteredNotes(Date from, Date to, List<String> words);

}
