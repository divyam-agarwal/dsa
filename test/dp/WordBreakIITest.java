package dp;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WordBreakIITest {
    WordBreakII sol = new WordBreakII();

    private ArrayList<String> dict(String... words) {
        return new ArrayList<>(Arrays.asList(words));
    }

    @Test
    void interviewBitExample() {
        List<String> result = sol.wordBreak("catsanddog", dict("cat", "cats", "and", "sand", "dog"));
        assertEquals(Arrays.asList("cat sand dog", "cats and dog"), result);
    }

    @Test
    void singleWordMatch() {
        List<String> result = sol.wordBreak("apple", dict("apple", "pen"));
        assertEquals(Arrays.asList("apple"), result);
    }

    @Test
    void noValidSegmentation() {
        List<String> result = sol.wordBreak("abcd", dict("ab", "cd", "abc"));
        // "ab"+"cd" works
        assertEquals(Arrays.asList("ab cd"), result);
    }

    @Test
    void noMatchAtAll() {
        List<String> result = sol.wordBreak("xyz", dict("ab", "cd"));
        assertEquals(new ArrayList<>(), result);
    }

    @Test
    void multipleWaysWithOverlap() {
        // "pineapple" → only "pine apple" if both are in dict
        List<String> result = sol.wordBreak("pineapple", dict("pine", "apple", "pineapple"));
        assertEquals(Arrays.asList("pine apple", "pineapple"), result);
    }

    @Test
    void resultIsSortedLexicographically() {
        // "catsand" → "cat sand" or "cats and" — sorted: "cat sand" < "cats and"
        List<String> result = sol.wordBreak("catsand", dict("cat", "cats", "and", "sand"));
        assertEquals(Arrays.asList("cat sand", "cats and"), result);
    }

    @Test
    void repeatedWordInSentence() {
        // "aaa" with dict ["a", "aa"] → "a a a", "a aa", "aa a"
        List<String> result = sol.wordBreak("aaa", dict("a", "aa"));
        assertEquals(Arrays.asList("a a a", "a aa", "aa a"), result);
    }

    @Test
    void singleCharacterString() {
        List<String> result = sol.wordBreak("a", dict("a", "b"));
        assertEquals(Arrays.asList("a"), result);
    }

    @Test
    void wordUsedMultipleTimes() {
        // "abab" with dict ["ab"] → only "ab ab"
        List<String> result = sol.wordBreak("abab", dict("ab"));
        assertEquals(Arrays.asList("ab ab"), result);
    }
}
