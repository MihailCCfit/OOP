package nsu.fit.tsukanov.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import nsu.fit.tsukanov.notebook.core.Configuration;
import nsu.fit.tsukanov.notebook.core.Note;
import nsu.fit.tsukanov.notebook.core.NoteBook;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class NoteBookService {
    private final NoteBook noteBook;

    public NoteBookService() throws IOException {
        File noteBookFile = new File(Configuration.noteBookPath());
        if (noteBookFile.exists()){
            ObjectMapper mapper = new ObjectMapper();
            noteBook = mapper.readValue(noteBookFile, NoteBook.class);
        }
        else{
            noteBook = new NoteBook(new AtomicLong(0), new TreeMap<>());
        }

    }
    private List<Map.Entry<String, Date>> getAllNotesWithDates(){
        return noteBook.dateTable().entrySet().stream().toList();
    }
    public List<String> getAllNotes(){
        return noteBook.dateTable().keySet().stream().toList();
    }
    public Note getNote(String noteName){
        File note = new File(Configuration.NoteBookFolder, "Note_"+noteName);
        if (note.exists()){
            try {
                return new ObjectMapper().readValue(note, Note.class);
            } catch (IOException e) {
                return null;
            }
        }
        else{
            return null;
        }
    }
    private List<Map.Entry<String, Date>> getAllFiltered(){
        return noteBook.dateTable().entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toList());
    }
    private List<Map.Entry<String, Date>> getAllFiltered(Date from, Date to){


    }
}
