import nsu.fit.tsukanov.Controller.NoteBookController;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

public class ServiceTester {
    @Test
    void simple() {
        int exitCode = new CommandLine(new NoteBookController()).execute("-add", "Some", "Text");
        System.exit(exitCode);
    }
}
