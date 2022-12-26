package nsu.fit.tsukanov.notebook.core;

public class Configuration {

    static public final String BasePath = "";
    static public final String BasicNoteBookName = "notebook";
    static public final String ConfName = "configuration.json";
    static public final String version = "1.1";
    static public String bookName = BasicNoteBookName;

    private Configuration() {
    }

    static public String configurationPath() {
        return BasePath + ConfName;
    }

    static public String noteBookPath() {
        return noteBookPath(null);
    }

    static public String noteBookPath(String noteBookName) {
        return BasePath + getNoteBookName(noteBookName) + ".json";
    }

    static public String getNoteBookName() {
        return BasicNoteBookName;
    }

    static public String getNoteBookName(String name) {
        if (name == null) {
            return getNoteBookName();
        }
        return name;
    }

}
