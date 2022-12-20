package nsu.fit.tsukanov.notebook.core;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.File;


public class Configuration {

    static public final String BasePath = "src/main/resources/";
    static public final String NoteBookFolder = "notes";
    static public final String NoteBookName = "notebook.json";

    static public final String ConfName = "configuration.json";

    static public String configurationPath() {
        return BasePath + ConfName;
    }
    static public String noteBookPath() {
        return BasePath + NoteBookName;
    }
    static public String noteFolderPath() {
        return BasePath + NoteBookFolder;
    }
//
//    private String NoteBookName;
//
//    public static Configuration getConfiguration() {
//        File file = new File(configurationPath())
//        return null;
//    }
//    @JsonCreator
//    public Configuration(String noteBookName) {
//        NoteBookName = noteBookName;
//    }
//
//    public String getNoteBookName() {
//        return NoteBookName;
//    }
//
//    public void setNoteBookName(String noteBookName) {
//        NoteBookName = noteBookName;
//    }
}
