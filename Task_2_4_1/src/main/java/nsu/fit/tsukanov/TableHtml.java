package nsu.fit.tsukanov;

import nsu.fit.tsukanov.model.evaluator.Assessment;

import javax.swing.text.html.HTML;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Map;

public class TableHtml {
    private HTML html;

    public TableHtml() {
        LinkedList<?> linkedList;
    }

    public void smth(OutputStream outputStream, Map<String, Assessment> assessmentMap) throws IOException {
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
                    font-size: 22px;
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
        String row = getCell("personName");
        for (Assessment value : assessmentMap.values()) {
            row += getCell(value);
        }
        addRow(outputStreamWriter, row);


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
