package nsu.fit.tsukanov.notebook.service;

import nsu.fit.tsukanov.notebook.core.Note;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Generates filtering, and string for representation.
 */
public class FilterBuilder {
    private Stream<Note> noteStream;
    private Date after = null;
    private Date before = null;

    FilterBuilder(Stream<Note> noteStream) {
        this.noteStream = noteStream;
    }

    FilterBuilder(List<Note> noteList) {
        this(noteList.stream());
    }

    /**
     * Filters all notes that after specified date.
     *
     * @param date the date by which all notes will be filtered.
     * @return this builder
     */
    public FilterBuilder after(Date date) {
        if (date == null) {
            return this;
        }
        if (after == null) {
            this.after = date;
        } else {
            throw new IllegalStateException("Date after already exists");
        }
        noteStream = noteStream.filter((x) -> x.date().after(date));
        return this;
    }

    /**
     * Filters all notes that before specified date.
     *
     * @param date the date by which all notes will be filtered.
     * @return this builder
     */
    public FilterBuilder before(Date date) {
        if (date == null) {
            return this;
        }
        if (before == null) {
            this.before = date;
        } else {
            throw new IllegalStateException("Date after already exists");
        }
        noteStream = noteStream.filter((x) -> x.date().before(date));
        return this;
    }

    /**
     * Choose notes that has any substring from specified words.
     *
     * @param words by which notes will be seperated
     * @return this builder
     */
    public FilterBuilder containsInHeader(List<String> words) {
        if (words != null) {
            noteStream = noteStream.filter((x) -> noteHas(x, words));
        }
        return this;
    }

    private static boolean stringContainsAny(String str, List<String> strings) {
        for (String string : strings) {
            if (str.contains(string)) {
                return true;
            }
        }
        return false;
    }

    private static boolean noteHas(Note note, List<String> strings) {
        return stringContainsAny(note.name(), strings);
    }

    /**
     * Return built list of notes. Validity check
     *
     * @return final list of notes
     */
    public List<Note> build() {
        if (after != null && before != null && after.after(before)){
            throw new IllegalStateException("Date \"after\" after the date \"before\"");
        }
        return noteStream.collect(Collectors.toList());
    }

    /**
     * Creates string of descriptions corresponded to the arguments.
     * Show how notes were filtered.
     *
     * @param after  the date after
     * @param before the date before
     * @param words  the list of words
     * @return nice string representation
     */
    static public String showFilter(Date after, Date before, List<String> words) {
        String basic = "Show all notes";
        if (after != null || before != null || words != null) {
            basic += " that";
        }
        String afterString = after != null ? " created after " + after : "";
        String beforeString = after != null ? " created before " + before : "";
        String wordsString = words != null ? " contains in the heading ["
                + words.stream().reduce((x, y) -> x + ", " + y).get() + "]" : "";
        return basic + afterString + beforeString + wordsString + ":";
    }

}
