package substrings.algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import substrings.Finder;



/**
 * Its dynamic implementation of <i>Aho–Korasick algorithm</i>.
 * If n - size of text, m - size of pattern, then
 *
 * <p>- preprocessing phase in O(m) time and space complexity;
 *
 * <p>- searching phase in O(n) time complexity;
 *
 * <p>It uses map of transitions in each State ({@code Vertex} of Automata).
 * So its finite automata.
 * Find occurs of pattern string in the text.
 * It must be dynamic. So finding algorithm should work character by character.
 * It allows to not save text in memory (RAM).
 *
 * @see ApostolicoCrochemoreFinder#nextChar(char)
 * @see ApostolicoCrochemoreFinder#setPattern(String)
 * @see Finder
 */
public class AhoKorasickFinder implements Finder {
    char[] pattern;
    private List<Integer> answers;
    Vertex[] vertices;
    int sz;
    int state = 0;
    int count = 0;

    private static class Vertex {
        Map<Character, Integer> next = new HashMap<>();
        boolean leaf = false;
        int pref = -1;
        char pch = '0';
        int link = -1;
        Map<Character, Integer> go = new HashMap<>();
    }


    /**
     * Initialize help array, pattern and other things for algorithm.
     *
     * @param pattern the pattern whose occurrences will be searched
     */
    public AhoKorasickFinder(String pattern) {
        setPattern(pattern);
    }

    /**
     * Initialize nothing.
     */
    public AhoKorasickFinder() {
        this.pattern = null;
    }


    /**
     * Clear previous state of support structures.
     * Set pattern into this finder, set some parameters and support things in specified way.
     *
     * @param pattern the string, occurrences  of which will be searched in the text.
     */
    @Override
    public void setPattern(String pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("null pattern");
        }
        answers = new ArrayList<>();
        this.pattern = pattern.toCharArray();
        this.count = 0;
        this.state = 0;

        vertices = new Vertex[pattern.length() + 1];
        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = new Vertex();
        }

        sz = 1;
        int v = 0;
        char[] arr = this.pattern;

        for (int i = 0; i < pattern.length(); i++) {
            char c = arr[i];
            if (!vertices[v].next.containsKey(c)) {
                vertices[sz].pref = v;
                vertices[sz].pch = c;
                vertices[v].next.put(c, sz++);
            }
            v = vertices[v].next.get(c);
        }
        vertices[v].leaf = true;
    }

    private int getNext(int v, char c) {

        return vertices[v].go.get(c);
    }

    private int go(int v, char c) {
        if (!vertices[v].go.containsKey(c)) {
            if (vertices[v].next.containsKey(c)) {
                vertices[v].go.put(c, vertices[v].next.get(c));
            } else {
                var next = v == 0 ? 0 : go(getLink(v), c);
                vertices[v].go.put(c, next);
            }
        }
        return getNext(v, c);
    }

    private int getLink(int v) {
        if (vertices[v].link == -1) {
            if (v == 0 || vertices[v].pref == 0) {
                vertices[v].link = 0;
            } else {
                var tmp = getLink(vertices[v].pref);
                vertices[v].link = go(tmp,
                        vertices[v].pch);
            }
        }
        return vertices[v].link;
    }

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

    @Override
    public void nextChar(char symbol) {
        if (noPattern()) {
            throw new IllegalStateException("There is no pattern");
        }
        state = go(state, symbol);
        count++;
        if (vertices[state].leaf) {
            answers.add(count - pattern.length);
        }

    }

    /**
     * Return arrayList with position of pattern occurrences in the text.
     *
     * @return arrayList with position of pattern occurrences in the text.
     */
    @Override
    public List<Integer> getAnswer() {
        return answers;
    }

    private boolean noPattern() {
        return pattern == null;
    }

}
