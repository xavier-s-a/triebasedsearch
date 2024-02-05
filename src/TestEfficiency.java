import edu.uwm.cs751.TrieBasedSimilaritySearch;
import junit.framework.TestCase;

import java.util.Random;
import java.util.Set;

public class TestEfficiency extends TestCase {
    TrieBasedSimilaritySearch trie;

    private static final int NUM_ELEMENTS = 1000000; // Adjust this as needed

    private static final int SHORT_WORD_MIN_LENGTH = 1;
    private static final int SHORT_WORD_MAX_LENGTH = 5;

    private static final int LONG_WORD_MIN_LENGTH = 6;
    private static final int LONG_WORD_MAX_LENGTH = 15;

    private static final int VERY_LONG_WORD_MIN_LENGTH = 16;
    private static final int VERY_LONG_WORD_MAX_LENGTH = 1000;

    @Override
    protected void setUp() {
        // Initialize the Trie for each test case
        trie = new TrieBasedSimilaritySearch();
    }

    public void testWordsEfficiency() {
        testInsertEfficiency(generateWordsArray(NUM_ELEMENTS, SHORT_WORD_MIN_LENGTH, SHORT_WORD_MAX_LENGTH), "short");
        testInsertEfficiency(generateWordsArray(NUM_ELEMENTS, LONG_WORD_MIN_LENGTH, LONG_WORD_MAX_LENGTH), "long");
        testInsertEfficiency(generateWordsArray(NUM_ELEMENTS, VERY_LONG_WORD_MIN_LENGTH, VERY_LONG_WORD_MAX_LENGTH), "very long");
    }

    private void testInsertEfficiency(String[] words, String wordType) {
        long startTime = System.currentTimeMillis();

        for (String word : words) {
            trie.insert(word);
        }

        long endTime = System.currentTimeMillis();

        long elapsedTime = endTime - startTime;
        System.out.println("Time taken to insert " + wordType + " words: " + elapsedTime + " milliseconds");

        // Assuming a linear time complexity for insertion, the time taken should be within a reasonable range

        long expectedMaxTime = 10000; // Maximum expected time in milliseconds

        if (elapsedTime > expectedMaxTime) {
            fail("Insertion time for " + wordType + " words exceeded the expected maximum time");
        }
    }

    public void testSearchEfficiency() {
        String[] words = WordGenerator.generateRandomWordsArray(NUM_ELEMENTS);

        // Insert generated words into the trie
        for (String word : words) {
            trie.insert(word);
        }

        long startTime = System.currentTimeMillis();

        for (String word : words) {
            trie.search(word);
        }

        long endTime = System.currentTimeMillis();

        long elapsedTime = endTime - startTime;
        System.out.println("Time taken to search " + NUM_ELEMENTS + " elements: " + elapsedTime + " milliseconds");

        // Assuming a linear time complexity for search, the time taken should be within a reasonable range

        long expectedMaxTime = 10000; // Maximum expected time in milliseconds

        if (elapsedTime > expectedMaxTime) {
            fail("Search time exceeded the expected maximum time");
        }
    }

    public void testFindSimilarWordsEfficiency() {
        String[] words = WordGenerator.generateRandomWordsArray(NUM_ELEMENTS);

        // Insert generated words into the trie
        for (String word : words) {
            trie.insert(word);
        }

        String targetWord = WordGenerator.generateRandomWord();

        long startTime = System.currentTimeMillis();

        Set<String> similarWords = trie.findSimilarWords(targetWord, 2); // Adjust the edit distance as needed

        long endTime = System.currentTimeMillis();

        long elapsedTime = endTime - startTime;
        System.out.println("Time taken to find similar words: " + elapsedTime + " milliseconds");

        // Assuming a reasonable time complexity for similarity search, the time taken should be within a range

        long expectedMaxTime = 10000; // Maximum expected time in milliseconds

        if (elapsedTime > expectedMaxTime) {
            fail("Similarity search time exceeded the expected maximum time");
        }
    }
    
    private String[] generateWordsArray(int numWords, int minLength, int maxLength) {
        String[] words = new String[numWords];

        for (int i = 0; i < numWords; i++) {
            int wordLength = generateRandomLength(minLength, maxLength);
            words[i] = generateWord(wordLength);
        }

        return words;
    }

    private int generateRandomLength(int minLength, int maxLength) {
        Random random = new Random();
        int halfLength = (minLength + maxLength) / 2;
        return random.nextInt(halfLength - minLength + 1) + minLength;
    }

    private String generateWord(int length) {
        StringBuilder word = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            char randomChar = (char) ('a' + random.nextInt(26));
            word.append(randomChar);
        }

        return word.toString();
    }

    static class WordGenerator {
        private static final int DEFAULT_WORD_LENGTH = 5;

        /**
         * Generate a random word with a default length.
         *
         * @return Randomly generated word.
         */
        public static String generateRandomWord() {
            return generateRandomWord(DEFAULT_WORD_LENGTH);
        }

        /**
         * Generate a random word with a specified length.
         *
         * @param length Length of the generated word.
         * @return Randomly generated word.
         */
        public static String generateRandomWord(int length) {
            StringBuilder word = new StringBuilder();
            Random random = new Random();

            for (int i = 0; i < length; i++) {
                char randomChar = (char) ('a' + random.nextInt(26));
                word.append(randomChar);
            }

            return word.toString();
        }

        /**
         * Generate an array of random words with a specified length.
         *
         * @param numWords Number of words to generate.
         * @param length   Length of each generated word.
         * @return Array of randomly generated words.
         */
        public static String[] generateRandomWordsArray(int numWords, int length) {
            String[] words = new String[numWords];

            for (int i = 0; i < numWords; i++) {
                words[i] = generateRandomWord(length);
            }

            return words;
        }

        /**
         * Generate an array of random words with the default length.
         *
         * @param numWords Number of words to generate.
         * @return Array of randomly generated words.
         */
        public static String[] generateRandomWordsArray(int numWords) {
            return generateRandomWordsArray(numWords, DEFAULT_WORD_LENGTH);
        }
    }
}
