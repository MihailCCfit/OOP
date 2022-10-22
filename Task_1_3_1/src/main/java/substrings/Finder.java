package substrings;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Find occurs of pattern string in the text.
 * It must be dynamic. So finding algorithm should work character by character.
 * It allows to not save text in memory (RAM).
 */
public interface Finder {

    /**
     * Find all pattern occurrences in specified inputStream.
     *
     * @param inputStream the inputStream in which patterns occurrences will be searched
     * @return arrayList with positions of pattern in inputStream.
     * @throws IOException when there are IO exception
     */
    default List<Integer> findPatternInText(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            throw new IllegalArgumentException("null inputStream");
        }
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        final int bufSize = 1 << 13;
        char[] buf = new char[bufSize];
        while (true) {
            int flag;
            flag = inputStreamReader.read(buf, 0, bufSize);
            if (flag == -1) {
                break;
            }
            for (int i1 = 0; i1 < flag; i1++) {
                nextChar(buf[i1]);
            }
        }
        return getAnswer();
    }

    /**
     * Clear previous state of support structures.
     * Set pattern into this finder, set some parameters and support things in specified way.
     *
     * @param pattern the string, occurrences  of which will be searched in the text.
     */
    void setPattern(String pattern);

    /**
     * Get next symbol, analyze this, changes the internal state of finder.
     * If there is occurrences of pattern in text,
     * then position of this will be saved in the answerArray.
     * This function is used for others algorithm,
     * and user can use this for other text representation
     * (pipes, sockets, buffer and etc).
     *
     * @param symbol next symbol in the text.
     */
    void nextChar(char symbol);

    /**
     * Return arrayList with position of pattern occurrences in the text.
     *
     * @return arrayList with position of pattern occurrences in the text.
     */
    List<Integer> getAnswer();

}
