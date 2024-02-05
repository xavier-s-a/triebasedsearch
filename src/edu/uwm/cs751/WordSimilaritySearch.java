package edu.uwm.cs751;

import java.util.Set;

public interface WordSimilaritySearch {

	/**
	 * Inserts a word into the Trie data structure. The method transforms the word
	 * to lowercase and then inserts each character sequentially. If a character is
	 * not already present at a certain TrieNode, a new TrieNode is created. After
	 * all characters are inserted, the last node is marked as the end of the word.
	 * 
	 * @param word cannot be null,The word to be inserted into the Trie. It's not
	 *             case-sensitive.
	 */
	boolean insert(String word);

	/**
	 * Searches for a word in the Trie data structure. The method transforms the
	 * word to lowercase and then checks for the presence of each character
	 * sequentially. If at any point the character is not found, the word is not in
	 * the Trie. If all characters are found, it checks if the last node is marked
	 * as the end of a word to determine the word's presence in the Trie.
	 * 
	 * @param word The word to be searched for in the Trie. It's not case-sensitive.
	 * @return true if the word is present in the Trie, false otherwise.
	 */
	boolean search(String word);

	/**
	 * Finds and returns a set of words from the Trie that are similar to the
	 * specified word within a given maximum edit distance. The method initializes a
	 * search from the root of the Trie and explores possible paths that are within
	 * the allowed edit distance.
	 *
	 * @param word        The target word to find similar words for.
	 * @param maxDistance The maximum edit distance allowed between the target word
	 *                    and similar words.
	 * @return A set of words that are similar to the target word within the
	 *         specified maximum edit distance.
	 * @throws IllegalArgumentException If the maxDistance is less than 0.
	 */
	Set<String> findSimilarWords(String word, int maxDistance);

}