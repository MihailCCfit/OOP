package nsu.fit.tsukanov.notebook.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;


@JsonSerialize
public record Note(
        @JsonProperty("name") String name,
        @JsonProperty("date") Date date,
        @JsonProperty("text") String text) {

    @Override
    public String toString() {
        return "Note" +
                "(" + name + "):\n" +
                "Date: " + date +
                "\nText:\n" + text;
    }
}
