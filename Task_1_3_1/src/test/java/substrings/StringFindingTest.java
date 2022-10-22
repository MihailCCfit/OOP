package substrings;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import substrings.algorithms.AhoKorasickFinder;
import substrings.algorithms.ApostolicoCrochemoreFinder;



/**
 * Test findings algorithms. There is Apostolico-Crochemore algorithm and Aho–Corasick algorithm.
 */
public class StringFindingTest {

    private static Stream<Finder> finderStream() {
        return Stream.of(new AhoKorasickFinder("ab"),
                new ApostolicoCrochemoreFinder("ab")
        );
    }

    @ParameterizedTest
    @MethodSource("finderStream")
    void test(Finder finder) throws IOException {
        String arr = "ab";
        finder.setPattern(arr);
        InputStream file = new FileInputStream("src/test/resources/txt.txt");
        Assertions.assertTrue(finder.findPatternInText(file).containsAll(List.of(0, 2, 4)));
        String str = "aabab";
        InputStream inputStream = new ByteArrayInputStream(str.getBytes());
        finder.setPattern(arr);
        finder.findPatternInText(inputStream);
        String pattern = "aab";
        String text = "abaaaab";
        finder.setPattern(pattern);
        finder.findPatternInText(new ByteArrayInputStream(text.getBytes()));
        Assertions.assertTrue(finder.getAnswer().contains(4));
        pattern = "aa";
        text = "aaaabab";
        finder.setPattern(pattern);
        Assertions.assertTrue(finder.findPatternInText(new ByteArrayInputStream(text.getBytes()))
                .containsAll(List.of(0, 1, 2)));
        pattern = "abcab";
        text = "abccababcabc";
        finder.setPattern(pattern);
        Assertions.assertTrue(finder.findPatternInText(
                new ByteArrayInputStream(text.getBytes())).contains(6));
        File noFile = new File("noFile");
        Assertions.assertThrows(IOException.class,
                () -> finder.findPatternInText(new FileInputStream(noFile)));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> finder.setPattern(null));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> finder.findPatternInText(null));
        arr = "abc";
        finder.setPattern(arr);
        file = new FileInputStream("src/test/resources/large.txt");
        Assertions.assertTrue(finder.findPatternInText(file).containsAll(List.of(0, 5)));
        Assertions.assertTrue(List.of(0, 5).containsAll(finder.findPatternInText(file)));
        Assertions.assertFalse(finder.getAnswer().containsAll(List.of(0, 4)));
    }

    @Test
    void testForNothing() {
        Finder finder = new AhoKorasickFinder();
        Assertions.assertThrows(IllegalStateException.class, () -> finder.nextChar('c'));
        Finder finder2 = new ApostolicoCrochemoreFinder();
        Assertions.assertThrows(IllegalStateException.class, () -> finder2.nextChar('c'));
    }

}
