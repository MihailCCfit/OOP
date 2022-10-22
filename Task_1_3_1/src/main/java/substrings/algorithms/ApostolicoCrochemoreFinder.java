package substrings.algorithms;

import java.util.ArrayList;
import java.util.List;
import substrings.Finder;



/**
 * Its dynamic implementation of <i>Apostolico-Crochemore algorithm</i>.
 * If n - size of text, m - size of pattern, then
 *
 * <p>- preprocessing phase in O(m) time and space complexity;
 *
 * <p>- searching phase in O(n) time complexity;
 *
 * <p>- performs 3/2n text character comparisons in the worst case.
 *
 * <p>It uses table of shifts {@code helpArray}.
 * Find occurs of pattern string in the text.
 * It must be dynamic. So finding algorithm should work character by character.
 * It allows to not save text in memory (RAM).
 *
 * @see ApostolicoCrochemoreFinder#nextChar(char)
 * @see ApostolicoCrochemoreFinder#setPattern(String)
 * @see Finder
 */
public class ApostolicoCrochemoreFinder implements Finder {
    private List<Integer> answers;
    private int[] helpArray;
    private char[] pattern;
    private List<Character> buffer;
    private int repeatingPrefixChar;
    private int offsetBorder;
    private int startOfSubstring;
    private int offsetPrefix;

    /**
     * Initialize nothing.
     */
    public ApostolicoCrochemoreFinder() {
        this.pattern = null;
    }

    /**
     * Initialize help array, pattern and other things for algorithm.
     *
     * @param pattern the pattern whose occurrences will be searched
     */
    public ApostolicoCrochemoreFinder(String pattern) {
        setPattern(pattern);
    }

    /**
     * Clear previous state of support structures.
     * Set pattern into this finder, set some parameters and support things in specified way.
     *
     * @param pattern the string, occurrences  of which will be searched in the text.
     */
    public void setPattern(String pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("null pattern");
        }
        answers = new ArrayList<>();
        helpArray = new int[pattern.length() + 1];
        this.pattern = pattern.toCharArray();
        this.buffer = new ArrayList<>();
        setPointers();
        setHelpArray();
    }

    private void setHelpArray() {
        int i = 0;
        int j = helpArray[0] = -1;
        while (i < pattern.length) {
            while (j > -1 && pattern[i] != pattern[j]) {
                j = helpArray[j];
            }
            i++;
            j++;
            if (i < pattern.length && pattern[i] == pattern[j]) {
                helpArray[i] = helpArray[j];
            } else {
                helpArray[i] = j;
            }
        }
    }



    private void setPointers() {
        repeatingPrefixChar = 1;
        while (repeatingPrefixChar < pattern.length
                && pattern[repeatingPrefixChar - 1] == pattern[repeatingPrefixChar]) {
            repeatingPrefixChar++;
        }
        if (repeatingPrefixChar == pattern.length) {
            repeatingPrefixChar = 0;
        }
        offsetBorder = repeatingPrefixChar;
        startOfSubstring = 0;
        offsetPrefix = 0;
    }

    /**
     * Get next symbol, analyze this, changes the internal state of finder.
     * If there is occurrences of pattern in text,
     * then position of this will be saved in the answerArray.
     * This function is used for others algorithm,
     * and user can use this for other text getters
     * (pipes, sockets, buffer and etc).
     *
     * @param symbol next symbol in the text.
     */
    public void nextChar(char symbol) {
        if (noPattern()) {
            throw new IllegalStateException("There is no pattern");
        }
        buffer.add(symbol);
        if (buffer.size() <= offsetBorder) {
            return;
        }
        if (offsetBorder < pattern.length && pattern[offsetBorder] == buffer.get(offsetBorder)) {
            ++offsetBorder;
            if (offsetBorder < pattern.length) {
                return;
            }
        }
        if (offsetBorder >= pattern.length) {
            while (offsetPrefix < repeatingPrefixChar
                    && pattern[offsetPrefix] == buffer.get(offsetPrefix)) {
                ++offsetPrefix;
            }
            if (offsetPrefix >= repeatingPrefixChar) {
                answers.add(startOfSubstring);
            }
        }
        startOfSubstring += offsetBorder - helpArray[offsetBorder];
        buffer.subList(0, (offsetBorder - helpArray[offsetBorder])).clear();
        if (offsetBorder == repeatingPrefixChar) {
            offsetPrefix = Integer.max(0, offsetPrefix - 1);
        } else if (helpArray[offsetBorder] <= repeatingPrefixChar) {
            offsetPrefix = Integer.max(0, helpArray[offsetBorder]);
            offsetBorder = repeatingPrefixChar;
        } else {
            offsetPrefix = repeatingPrefixChar;
            offsetBorder = helpArray[offsetBorder];
        }
    }

    /**
     * Return arrayList with position of pattern occurrences in the text.
     *
     * @return arrayList with position of pattern occurrences in the text.
     */
    public List<Integer> getAnswer() {
        return answers;
    }

    private boolean noPattern() {
        return pattern == null;
    }
}
