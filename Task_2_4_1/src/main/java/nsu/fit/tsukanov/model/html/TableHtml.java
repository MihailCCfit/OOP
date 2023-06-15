package nsu.fit.tsukanov.model.html;

import nsu.fit.tsukanov.model.entity.tasks.Task;
import nsu.fit.tsukanov.model.evaluator.Assessment;

import javax.swing.text.html.HTML;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TableHtml {
    private HTML html;

    public TableHtml() {
        LinkedList<?> linkedList;
    }

    public void smth(OutputStream outputStream, Map<String, Map<String, Assessment>> studentsAssessmentMap, List<Task> tasks) throws IOException {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        outputStreamWriter.write("""
                <!DOCTYPE html>
                <html>
                <style>
                table {
                    border-collapse: collapse;
                }

                td {
                    border: 1px solid black;
                    line-height: 1.5em;
                    font-size: 14px;
                    font-family: monospace;
                    text-align: center;
                    font-weight: normal;
                }

                h1 {
                    font-family: monospace;
                    font-size: 24px;
                    font-weight: normal;
                }
                </style>

                <head>
                <title>%s</title>
                </head>
                <body>
                                <h1>%s</h1>
                <table style="width:90%%">
                                
                """.formatted("group21214", "group21214"));
        String rowTask = getCell("names");
        for (Task task : tasks) {
            rowTask += getCell(task.id());
        }
        addRow(outputStreamWriter, rowTask);
        Map<String, String> studentsRows = new HashMap<>();
        List<String> studentList = studentsAssessmentMap.keySet().stream().toList();
        studentList.forEach((gitName) -> {
            studentsRows.put(gitName, getCell(gitName));
        });

        for (Task task : tasks) {
            for (String gitName : studentList) {
                String prev = studentsRows.get(gitName);
                Assessment assessmentForTask = studentsAssessmentMap.get(gitName).get(task.id());
                String newRow = prev
                        + getCell(assessmentForTask != null ? assessmentForTask.formalize() : new Assessment());
                studentsRows.put(gitName, newRow);
            }
        }
        for (String gitName : studentList) {
            addRow(outputStreamWriter, studentsRows.get(gitName));
        }

        outputStreamWriter.write("""
                </table>
                </body>
                </html>
                """);
        outputStreamWriter.flush();
        outputStreamWriter.close();
    }

    private void addRow(OutputStreamWriter writer, String str) throws IOException {
        writer.write("<tr>\n");
        writer.write(str);
        writer.write("</tr>\n");
    }

    private String getCell(String str) {
        return "<td>" + str + "</td>\n";
    }

    private String getCell(Object... objects) {
        StringBuilder answer = new StringBuilder();
        for (Object object : objects) {
            answer.append("<td>").append(object.toString()).append("</td>\n");
        }
        return answer.toString();
    }


}
