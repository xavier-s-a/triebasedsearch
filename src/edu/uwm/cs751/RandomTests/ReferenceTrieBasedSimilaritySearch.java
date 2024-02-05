package edu.uwm.cs751.RandomTests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// This file was created using the ReferenceIntBag.class file from Lab #2 as a template

/**
 * An implementation of the Trie Based Similarity Search for strings.
 */
public class ReferenceTrieBasedSimilaritySearch {
	
	private Set<String> wrapped;
	
	/*
	 * Create a string
	 */
	public ReferenceTrieBasedSimilaritySearch()
	{	
		wrapped = new HashSet<String>();
	}
	
	// Helper methods
	
	private boolean isValidWord(String word)
	{
		if (word.length() == 1 && word.equals(" "))
		{
			return true;
		}
		
		if (word == null || word.isEmpty())
		{
			throw new IllegalArgumentException();
		}
		
		for (char ch : word.toCharArray())
		{
			if (!((ch >= 'a' && ch <= 'z')))
			{
				throw new IllegalArgumentException();
			}
		}
		
		return true;
	}
	
	private static int editDistance(String a, String b)
	{
		int [][] dp = new int[a.length() + 1][b.length() + 1];
				
		for (int i = 0; i <= a.length(); i++)
		{
			for (int j = 0; j <= b.length(); j++)
			{
				if (i == 0)
				{
					dp[i][j] = j;
				}
				else if (j == 0)
				{
					dp[i][j] = i;
				}
				else
				{
					dp[i][j] = Math.min(Math.min(dp[i - 1][j - 1] + 
							(a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1), 
							dp[i - 1][j] + 1), dp[i][j - 1] + 1);
				}
			}
		}
		
		return dp[a.length()][b.length()];
	}
	
	/**
	 * Inserts a word into the Trie data structure. The method transforms the word
	 * to lowercase and then inserts each character sequentially. If a character is
	 * not already present at a certain TrieNode, a new TrieNode is created. After
	 * all characters are inserted, the last node is marked as the end of the word.
	 * 
	 * @param word The word to be inserted into the Trie. It's not case-sensitive.
	 */
	public boolean insert(String word)
	{
		isValidWord(word);
		return wrapped.add(word);
	}
	
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
	public boolean search(String word)
	{
		isValidWord(word);
		return wrapped.contains(word);
	}
	
	/**
	 * Finds and returns a set of words from the Trie that are similar to the
	 * specified word within a given maximum edit distance. The method initializes a
	 * search from the root of the Trie and explores possible paths that are within
	 * the allowed edit distance.
	 * @param word        The target word to find similar words for.
	 * @param maxDistance The maximum edit distance allowed between the target word
	 *                    and similar words.
	 * @return A set of words that are similar to the target word within the
	 *         specified maximum edit distance.
	 */
	public Set<String> findSimilarWords(String word, int maxDistance) {
		
		List<String> similarWords = new ArrayList<String>();
		
		// Iterating through the list via for-each loop
		for (String dictWord : wrapped)
		{
			if (editDistance(word, dictWord) <= maxDistance)
			{
				similarWords.add(dictWord);
			}
		}
		
		Collections.sort(similarWords);
		
		// Convert the sorted list back to a HashSet
		Set<String> sortedSimilarWords = new HashSet<>(similarWords);
		
		return sortedSimilarWords;
	}
}
