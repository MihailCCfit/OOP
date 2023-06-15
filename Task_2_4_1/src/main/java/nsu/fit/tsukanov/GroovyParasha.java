package nsu.fit.tsukanov;

import nsu.fit.tsukanov.model.GroovyModel;
import nsu.fit.tsukanov.model.html.TableHtml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class GroovyParasha {

    public static void main(String[] args) {
        File generalDir = new File("newFolder");
        File scriptsDir = new File("scripts");
        GroovyModel groovyModel = new GroovyModel(scriptsDir, generalDir);
        TableHtml tableHtml = new TableHtml();
        File htmlDir = new File("htmls");
        htmlDir.mkdir();
        File htmlFile = new File(htmlDir, "table.html");
        try {
            htmlFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            tableHtml.smth(new FileOutputStream(htmlFile), groovyModel.evaluatePersons(List.of("MihailCCfit")),
                    groovyModel.taskConfig.getTasks());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}