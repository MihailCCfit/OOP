package nsu.fit.tsukanov.notebook.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicLong;

@JsonSerialize
public record NoteBook(
        @JsonProperty("id") AtomicLong ID_GENERATOR,
        @JsonProperty("dateTable") TreeMap<String, Date> dateTable) {

    @Override
    public String toString() {
        return "Notebook{" +
                ", ID_GENERATOR=" + ID_GENERATOR +
                ", dateTable=" + dateTable +
                '}';
    }
}
