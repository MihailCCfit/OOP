package nsu.fit.tsukanov.notebook.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.TreeMap;

@JsonSerialize
public record NoteBook(@JsonProperty("name") String noteBookName,
                       @JsonProperty("noteTable") TreeMap<String, Note> noteTable) {

    @Override
    public String toString() {
        return
                "<" + noteBookName +
                        "> \nnotes:\n" + noteTable;
    }
}
