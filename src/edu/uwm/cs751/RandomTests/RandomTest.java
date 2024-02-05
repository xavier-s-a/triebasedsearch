package edu.uwm.cs751.RandomTests;

import java.util.Arrays;
import java.util.Collections;

//This file was created using the RandomTest.class file from Lab #2 as a template

import java.util.Random;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;


import edu.uwm.cs.random.AbstractRandomTest;
import edu.uwm.cs.random.Command;
import edu.uwm.cs.random.LiteralBuilder;
import edu.uwm.cs751.TrieBasedSimilaritySearch;
import edu.uwm.cs751.RandomTests.ReferenceTrieBasedSimilaritySearch;
import edu.uwm.cs.util.TriFunction;

@SuppressWarnings("unused")
public class RandomTest extends AbstractRandomTest<ReferenceTrieBasedSimilaritySearch, TrieBasedSimilaritySearch> {

	private static final int MAX_TESTS = 1_000_000;
	private static final int DEFAULT_MAX_TEST_LENGTH = 1_000;
	
	protected RandomTest()
	{
		super(ReferenceTrieBasedSimilaritySearch.class, TrieBasedSimilaritySearch.class, "TrieBasedSimilaritySearch", "ps", MAX_TESTS, DEFAULT_MAX_TEST_LENGTH);
	}
	
	private int randomEditDistance(String word)
	{
		int max = (word.length() - 1);
		int min = 0;
		
		int editDistance = ((int)Math.floor(Math.random() * (max - min + 1) + min));
		
		return editDistance;
	}
	
	protected String randomString(Random r)
	{
		int wordLength = 1;
		while (r.nextBoolean()) {
			++wordLength;
		}
		// int wordLength = (int) (Math.random() * 10) + 1; // Random word length between 1 and 10 characters
        StringBuilder word = new StringBuilder();
        for (int i = 0; i < wordLength; i++) {
            char randomChar = (char) ('a' + Math.random() * 26);
            word.append(randomChar);
        }
        return word.toString();
	}
	
	@Override // decorate
	public String toString(Object x) {
		if (x instanceof Set<?>) {
			Set<?> s = (Set<?>)x;
			if (s.isEmpty()) return "Collections.emptySet()";
			if (s.size() == 1) return "Collections.singleton(" + toString(s.iterator().next()) + ")";
			StringBuilder sb = new StringBuilder();
			sb.append("new HashSet<>(Arrays.asList(");
			boolean first = true;
			for (Object elem : s) {
				if (first) first = false;
				else sb.append(",");
				sb.append(toString(elem));
			}
			sb.append("))");
			return sb.toString();
		} else return super.toString(x);
	}
	
	private Command<?> newTrieCommand = newCommand();
	private BiFunction<Integer, String, Command<?>> insertCommand = 
			build(lift(ReferenceTrieBasedSimilaritySearch::insert), 
			lift(TrieBasedSimilaritySearch::insert), "insert");
	private BiFunction<Integer, String, Command<?>> searchCommand = 
			build(lift(ReferenceTrieBasedSimilaritySearch::search), 
			lift(TrieBasedSimilaritySearch::search), "search");
	private TriFunction<Integer, String, Integer, Command<?>> similarWordsCommand = 
			build(lift(ReferenceTrieBasedSimilaritySearch::findSimilarWords), 
			lift(TrieBasedSimilaritySearch::findSimilarWords), "findSimilarWords");
	
	@Override
	protected Command<?> randomCommand(Random r)
	{
		int n = mainClass.size();
		if (n == 0) return newTrieCommand;
		int w = r.nextInt(n);
		
		switch (r.nextInt(16))
		{
		
			default:
			case 0:
				return newTrieCommand;
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
				return insertCommand.apply(w, randomString(r));
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
				return searchCommand.apply(w, randomString(r));
			case 11:
			case 12:
			case 13:
			case 14:
			case 15:
				String word = randomString(r);
				return similarWordsCommand.apply(w, word, randomEditDistance(word));
		}	
	}
	
	@Override
	public void printImports()
	{
		super.printImports();
		System.out.println("import java.util.Arrays;");
		System.out.println("import java.util.Collections;");
		System.out.println("import java.util.HashSet;");
		System.out.println("import edu.uwm.cs751.TrieBasedSimilaritySearch\n");
	}
	
	public static void main(String[] args)
	{
		RandomTest rt = new RandomTest();
		rt.run();
	}
		
}
	
	