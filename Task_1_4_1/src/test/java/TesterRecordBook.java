import ParserJSONstudentsData.ParserJsonStudentsData;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import studentsData.core.RecordBook;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TesterRecordBook {
    @Test
    void testJS() throws IOException, ParseException {
        FileReader fileReader;
        fileReader = new FileReader("src/test/resources/js.json");
        JSONParser jsonParser = new JSONParser();
        JSONObject object = (JSONObject) jsonParser.parse(fileReader);

        RecordBook recordBook = ParserJsonStudentsData.recordBookParse(
                object);
        Assertions.assertTrue(recordBook.hasHighScholarship());
        Assertions.assertTrue(recordBook.getAverage()>4);
        System.out.println(recordBook.shortInfo());
        System.out.println(recordBook);
        FileWriter fileWriter = new FileWriter("Output.txt");
        fileWriter.write(recordBook.toString());
        fileWriter.flush();
    }
}
