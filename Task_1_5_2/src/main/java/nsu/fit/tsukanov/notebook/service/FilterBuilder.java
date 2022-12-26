package nsu.fit.tsukanov.notebook.service;

import nsu.fit.tsukanov.notebook.core.Note;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilterBuilder {
    private Stream<Note> noteStream;

    FilterBuilder(Stream<Note> noteStream) {
        this.noteStream = noteStream;
    }

    FilterBuilder(List<Note> noteList) {
        this(noteList.stream());
    }

    public FilterBuilder after(Date date) {
        if (date != null) {
            noteStream = noteStream.filter((x) -> x.date().after(date));
        }
        return this;
    }

    public FilterBuilder before(Date date) {
        if (date != null) {
            noteStream = noteStream.filter((x) -> x.date().before(date));
        }
        return this;
    }

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


    public List<Note> build() {
        return noteStream.collect(Collectors.toList());
    }

    static public String showFilter(Date after, Date before, List<String> words) {
        String basic = "Show all notes";
        if (after != null || before != null || words != null) {
            basic += " that";
        }
        String afterString = after != null ? " created after " + after : "";
        String beforeString = after != null ? " created before " + before : "";
        String wordsString = words != null ? " contains in the heading ["
                + words.stream().reduce((x, y) -> x+ ", " + y).get() + "]": "";
        return basic + afterString + beforeString + wordsString + ":";
    }

}
