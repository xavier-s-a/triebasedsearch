
package edu.uwm.cs751;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Consumer;

import java.util.Random;

public class TrieBasedSimilaritySearch {

	/**
	 * Represents a node in the Trie data structure. Each TrieNode can represent an
	 * alphabet character and contains links to its children.
	 */
	private static class TrieNode {

		TrieNode[] children;
		boolean isEndOfWord;
		private static final char finder = 'a';
		private static final int size = 26;

		/**
		 * Initializes a new TrieNode with 26 possible children (for each letter of the
		 * alphabet) and sets the end of word flag to false.
		 */
		public TrieNode() {
			children = new TrieNode[size];
			isEndOfWord = false;

		}

		/**
		 * Sets a child TrieNode for a specific character.
		 * 
		 * @param ch   The character for which the child node should be set. Expected to
		 *             be a lowercase alphabet.
		 * @param node The TrieNode to set as the child for the given character.
		 */
		private void setChild(char ch, TrieNode node) {
			children[ch - finder] = node;
		}

		/**
		 * Returns the child TrieNode for a specific character.
		 * 
		 * @param ch The character for which the child node should be retrieved.
		 *           Expected to be a lowercase alphabet.
		 * @return The child TrieNode corresponding to the given character, or null if
		 *         no child is set for that character.
		 */
		public TrieNode getChild(char ch) {
			return children[ch - finder];
		}

		/**
		 * Checks if this TrieNode marks the end of a word.
		 *
		 * @return true if this node represents the end of a word, false otherwise.
		 */
		public boolean isEndOfWord() {
			return isEndOfWord;
		}

		/**
		 * Sets the flag indicating whether this TrieNode marks the end of a word.
		 * 
		 * @param val true to mark this node as the end of a word, false otherwise.
		 */
		public void setEndOfWord(boolean val) {
			isEndOfWord = val;
		}
	}

	// The root node of the Trie, representing the starting point of all inserted
	// strings.
	private TrieNode root;
	int total;

	/**
	 * Initializes a new Trie with a root node. For string operations to be
	 * performed on the Trie.
	 */
	public TrieBasedSimilaritySearch() {
		root = new TrieNode();
		total = 0;
		assert wellFormed() : "invariant failed at end of constructor";
	}

	private static Consumer<String> reporter = (s) -> System.out.println("Invariant error: " + s);

	private static boolean report(String error) {
		reporter.accept(error);
		return false;
	}

	/**
	 * To check the health of the Trie data structure
	 * 
	 * @return is wellformed or not
	 */
	private boolean wellFormed() {
		// 1. root node should not be null
		if (root == null)
			return report("Root of Trie cannot be null");

		// 2. There should not be any cycle
		// 3. maximum children nodes can be 26
		if (cycle(root, new HashSet<TrieNode>()))
			return report("Found cycle/Children length is less than 26 in Trie");

		// 4. if Children is empty then isEndOfWord should be true
		if (!checkEndOfWord(root))
			return report("isEndOfWord should be true if all the element of children are null except for the root");

		// 5. different nodes point to same child but does not forms cycle.
		if (isDiffParentSameChild(root, new HashSet<TrieNode>()))
			return report("Different parents are pointing to same child node");

		// If no problems found, then return true:
		return true;
	}

	/**
	 * Helper method for invariant to check if different parent pointing to same
	 * child
	 * 
	 * @param r   root node
	 * @param set use to store the nodes
	 * @return if child has multiple parent
	 */
	private boolean isDiffParentSameChild(TrieNode r, Set<TrieNode> set) {
		if (r == null)
			return false;
		for (int i = 0; i < 26; i++) {
			set.add(r);
			if (set.contains(r.children[i]))
				return true;
			if (isDiffParentSameChild(r.children[i], set))
				return true;
		}
		return false;
	}

	/**
	 * Helper method for invariant to check cycle
	 * 
	 * @param r   root node
	 * @param set use to store the parent
	 * @return if cycle present
	 */
	private boolean cycle(TrieNode r, Set<TrieNode> set) {
		if (r == null)
			return false;
		if (r.children == null)
			return true;
		if (r.children.length != 26)
			return true;
		for (int i = 0; i < 26; i++) {
			set.add(r);
			if (set.contains(r.children[i])) {
				return true;
			} else {
				boolean flag = cycle(r.children[i], set);
				if (flag == true)
					return true;
			}
			set.remove(r);

		}
		return false;
	}

	/**
	 * Helper method for Invariant to check isEndOfWord status is correct
	 * 
	 * @param r root node
	 * @return isEndOfWord status is correct
	 */
	private boolean checkEndOfWord(TrieNode r) {

		for (int i = 0; i < 26; i++) {
			TrieNode n = r.children[i];
			if (n != null) {
				if ((n != root) && ((!n.isEndOfWord && !hasNonNullChild(n)) || !checkEndOfWord(n)))
					return false;
			}
		}
		return true;
	}

	/**
	 * Helper method to check if a node has any non-null child
	 * 
	 * @param node
	 * @return if there is any Non null child there in the given node
	 */
	private boolean hasNonNullChild(TrieNode node) {
		for (TrieNode child : node.children) {
			if (child != null) {
				return true;
			}
		}
		return false;
	}

	private TrieBasedSimilaritySearch(boolean testInvariant) {
	}

	/**
	 * Inserts a word into the Trie data structure. The method transforms the word
	 * to lowercase and then inserts each character sequentially. If a character is
	 * not already present at a certain TrieNode, a new TrieNode is created. After
	 * all characters are inserted, the last node is marked as the end of the word.
	 * 
	 * @param word cannot be null,The word to be inserted into the Trie. It's not
	 *             case-sensitive.
	 */
	public boolean insert(String word) {
		assert wellFormed() : "invariant failed at start of insert";
		boolean wordIsNew = false;
		if (isValidWord(word)) {
			if (word.equals(" ")) {
				if (root.isEndOfWord == false) {
					wordIsNew = true;
					root.isEndOfWord = true;
				}
			} else {
				TrieNode node = root;
				for (int i = 0; i < word.length(); i++) {
					char ch = word.charAt(i);
					if (node.getChild(ch) == null)
						node.setChild(ch, new TrieNode());
					node = node.getChild(ch);
				}
				if (!node.isEndOfWord) {
					wordIsNew = true;
					node.isEndOfWord = true;
				}
			}
		}
		assert wellFormed() : "invariant failed at end of insert";
		return wordIsNew;
	}

	private boolean isValidWord(String word) {
		if (word.length() == 1 && word.equals(" "))
			return true;
		if (word == null || word.isEmpty())
			throw new IllegalArgumentException("Word is empty");
		for (char ch : word.toCharArray())
			if (!((ch >= 'a' && ch <= 'z')))
				throw new IllegalArgumentException("Word contains unsupported and non-alphabets");
		return true;
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
	public boolean search(String word) {
		assert wellFormed() : "invariant failed at start of search";
		isValidWord(word);
		if (word.equals(" "))
			return root.isEndOfWord == true;
		TrieNode node = root;
		word = word.toLowerCase();
		for (int i = 0; i < word.length(); i++) {
			char ch = word.charAt(i);
			if (node.getChild(ch) == null)
				return false;
			node = node.getChild(ch);
		}
		return node.isEndOfWord == true;
	}

	/**
	 * Class for internal testing
	 *
	 */
	public static class Spy {
		/**
		 * A public version of the data structure's internal node class.
		 */
		public static class Node extends TrieNode {
			/**
			 * Create a node with null data.
			 */
			public Node() {
				this(new Node[26], false);
			}

			/**
			 * Create a node with the given values
			 * 
			 * @param c data for new node, may be null
			 * @param n next for new node, may be null
			 */
			public Node(Node[] c, boolean endOfWord) {
				super();
				this.children = c;
				this.isEndOfWord = endOfWord;
			}
		}

		/**
		 * Create a node for testing.
		 * 
		 * @param c         TrieNode array
		 * @param endOfWord tells whether next node is there
		 * @return newly created test node
		 */
		public Node newNode(Node[] c, boolean endOfWord) {
			return new Node(c, endOfWord);
		}

		/**
		 * Change a node's next field
		 * 
		 * @param n1 node to change
		 * @param n2 node to point to
		 */
		public void setNext(Node n, int v, Node n2) {
			n.children[v] = n2;
		}

		/**
		 * Create an instance of the ADT with give data structure. This should only be
		 * used for testing.
		 * 
		 * @param r root of Trie
		 * @return instance of TrieBasedSimilaritySearch with the given field values.
		 */
		public TrieBasedSimilaritySearch create(Node r) {
			TrieBasedSimilaritySearch result = new TrieBasedSimilaritySearch(false);
			result.root = r;
			return result;
		}

		/**
		 * Return whether the wellFormed routine returns true for the argument
		 * 
		 * @param s transaction seq to check.
		 * @return
		 */
		public boolean wellFormed(TrieBasedSimilaritySearch s) {
			return s.wellFormed();
		}

	}

	/**
	 * Prints all the words in the Trie.
	 */
	public void printTrie() {
		printTrieHelper(root, "");
	}

	/**
	 * Recursive helper method to print all words in the Trie from a given node.
	 * 
	 * @param node The current node being visited.
	 * @param word The word formed from the root to the current node.
	 */
	private void printTrieHelper(TrieNode node, String word) {

		if (node == null) {
			return;
		}

		if (node.isEndOfWord()) {
			System.out.println(word);
		}
		for (int i = 0; i < 26; i++) {
			if (node.children[i] != null) {
				char ch = (char) ('a' + i);
				printTrieHelper(node.children[i], word + ch);
			}
		}
	}

	/**
	 * Adds all the words from the file path provided in the command line
	 * 
	 * @param filePath contains list of words
	 * @param trie     to which the words need to be inserted
	 */
	private static boolean insertAllFromFile(Path filePath, TrieBasedSimilaritySearch trie) {
		if (!Files.exists(filePath) || !Files.isRegularFile(filePath)) {
			System.out.println(filePath.getFileName() + " is invalide file.");
			return false;
		}
		System.out.println("Inserting all the words from " + filePath.getFileName());
		try (BufferedReader br = new BufferedReader(new FileReader(filePath.toFile()))) {
			String word;
			while ((word = br.readLine()) != null) {
				trie.insert(word);
			}
		} catch (IOException e) {
			System.err.println("Not able to read the file: " + e.getMessage());
		}
		return true;
	}

	/**
	 * Add word to file
	 * 
	 * @param filePath In which word need to be added.
	 * @param word     that need to be added.
	 */
	private static void writeWordToFile(Path filePath, String word) {
		try (FileWriter fw = new FileWriter(filePath.toFile(), true)) {
			fw.write(word);
			fw.write(System.lineSeparator());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// created with help of
		// https://www.geeksforgeeks.org/java-program-to-implement-levenshtein-distance-computing-algorithm/

	/**
	 * HELPER FOR edit distance Calculates the edit distance (Levenshtein distance)
	 * between two strings(length less than or equal to 40). The edit distance is the minimum number of operations
	 * required to transform one string into the other, where an operation is
	 * defined as an insertion, deletion, or substitution of a single character.
	 *
	 * @param a The first string to be compared.
	 * @param b The second string to be compared.
	 * @return The edit distance between the two provided strings.
	 */
	private static int editDistance(String a, String b ) {
		int[][] dp = new int[a.length() + 1][b.length() + 1];

		for (int i = 0; i <= a.length(); i++) {
			for (int j = 0; j <= b.length(); j++) {
				if (i == 0) {
					dp[i][j] = j;
				} else if (j == 0) {
					dp[i][j] = i;
				} else {
					dp[i][j] = Math.min(
							Math.min(dp[i - 1][j - 1] + (a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1), dp[i - 1][j] + 1),
							dp[i][j - 1] + 1);
				}



			}
		}

		return dp[a.length()][b.length()];
	}

	

	/**
	 * Calculates the minimum depth at which a leaf node is found from a given Trie node.
	 * This method is a part of the Length Filtering technique as outlined in section 3.2.1
	 * of the reference paper(Section 3.2 Page 3), Length Filtering.
	 *
	 * The method used for assessing whether the descendants of a Trie node have a length difference 
	 * larger than a specified threshold t compared to a query string. If all descendant 
	 * leaf nodes of a node are beyond this length difference, the node is deemed irrelevant
	 * for queries within an edit distance t, enabling efficient pruning of the search space.
	 *
	 * @param node         The current Trie node being examined.
	 * @param currentDepth The depth of the current node from the root.
	 * @return The minimum depth of a leaf node if present; otherwise,
	 *         Integer.MAX_VALUE.
	 */
	//This method is a part of the Length Filtering technique as outlined in section 3.2.1
	private int findMinLeafDepth(TrieNode node, int currentDepth) {
		// if the node is null, return maximum value as no leaf can be found
		if (node == null)
			return Integer.MAX_VALUE;

		// If the current node is the end of a word, return the current depth
		if (node.isEndOfWord)
			return currentDepth;

		// Initialize minimum depth to maximum value
		int minDepth = Integer.MAX_VALUE;

		// Recursively find the minimum depth among all children
		for (TrieNode child : node.children) {
			minDepth = Math.min(minDepth, findMinLeafDepth(child, currentDepth + 1));
		}
		return minDepth;
	}
	

	/**
	 * Finds words in the Trie that are similar to the given word within a specified
	 * edit distance. This method uses a depth-first search approach to explore the
	 * Trie and check for words that are within the maximum allowed edit distance
	 * from the given word.It incorporates The Length Filtering technique, as detailed
	 * in section 3.2.1 (Length Filtering) of the reference paper (Section 3.2, Page 3).
	 *
	 * @param node        The current Trie node being examined.
	 * @param word        The target word to which we are finding similar words.
	 * @param currentWord The current word formed from the path in the Trie up to
	 *                    this point.
	 * @param maxDistance The maximum allowed edit distance between the target word
	 *                    and similar words.
	 * @param result      A set to store the similar words found in the Trie.
	 */
	private void findSimilarWords(TrieNode node, String word, String currentWord, int maxDistance, Set<String> result) {

		// return if the current node is null
		if (node == null)
			return;


		int currentDepth = currentWord.length();

		// Calculate the minimum and maximum leaf depths from the current node
		int minLeafDepth = findMinLeafDepth(node, currentDepth);

		int queryLength = word.length();

		// Prune the search if the current node cannot lead to a valid match
		if (minLeafDepth - queryLength > maxDistance) {
			return; // cannot find any valid match
		}


		int currentEditDistance; 
		
		if(word.length()>40)
			currentEditDistance=computeEditDistance(word, currentWord,maxDistance); 
		else
			currentEditDistance=editDistance(word, currentWord); 


		if (currentEditDistance <= maxDistance && node.isEndOfWord()) {


			result.add(currentWord);
		}

		// Recursively search for similar words in all child nodes
		for (int i = 0; i < 26; i++) {
			if (node.children[i] != null) {
				char ch = (char) ('a' + i);
				TrieNode child = node.children[i];
				 {
					findSimilarWords(child, word, currentWord + ch, maxDistance, result);
				}
			}
		}
	}

	/**
	 * Computes the edit distance between two strings, D and Q, using a dynamic programming approach with a specified threshold.
	 * This method is an implementation of the threshold 
	 * edit distance algorithm as described in Part 3(Page 3), Section 3.1 (Edit distance computation)
	 * of the paper under the section "TECHNIQUES". 
	 * It is optimized for larger strings, particularly effective for lengths of 40 characters or more.
	 *
	 * The algorithm constructs a matrix, M, where each cell M[i,j] 
	 * represents the edit distance between the prefixes of D and Q
	 * of lengths i and j, respectively. 
	 * The matrix is filled considering only the relevant k-diagonals within the specified threshold, t.
	 * This targeted approach significantly reduces the computational complexity, especially for large strings.
	 *
	 * The method employs an early termination strategy. If the edit distance in all elements of a row exceeds the threshold,
	 * the computation halts early, concluding that the strings are not similar within the given threshold.
	 *
	 * @param D The first string to compare, typically representing the data string.
	 * @param Q The second string to compare, typically representing the query string.
	 * @param t The threshold for edit distance. If the edit distance exceeds this value, the method returns -1.
	 * @return The edit distance between the two strings if it is within the threshold; otherwise, -1.
	 */
	//edit distance algorithm as described in Part 3(Page 3), Section 3.1 (Edit distance computation)
	private static int computeEditDistance(String D, String Q, int t) {
	    int n = D.length();
	    int m = Q.length();
	    int[][] M = new int[n + 1][m + 1];
	  
	    // Initialize boundary conditions
	    for (int i = 0; i <= n; i++) M[i][0] = i;
	    for (int j = 0; j <= m; j++) M[0][j] = j;

	    // Compute only the required diagonals
	    for (int i = 1; i <= n; i++) {
	        int start = Math.max(1, i - t);
	        int end = Math.min(m, i + t);
	        boolean exceededThreshold = true;

	        for (int j = start; j <= end; j++) {
	            int cost = (D.charAt(i - 1) == Q.charAt(j - 1)) ? 0 : 1;
	            M[i][j] = Math.min(M[i - 1][j - 1] + cost, Math.min(M[i - 1][j] + 1, M[i][j - 1] + 1));

	            if (M[i][j] <= t) {
	                exceededThreshold = false;
	            }
	        }

	        // Early termination check
	        if (exceededThreshold) {
	            return -1; // Strings are not similar within t
	        }
	    }

	    return M[n][m];
	}

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
	public Set<String> findSimilarWords(String word, int maxDistance) {
		// Check for valid maxDistance
		if (maxDistance < 0)
			throw new IllegalArgumentException("Less than 0 is not allowed");

		Set<String> result = new HashSet<>();
		// Start the recursive search from the root of the Trie
		findSimilarWords(root, word, "", maxDistance, result);
		return result;
	}

	// took from
	// https://www.geeksforgeeks.org/how-to-print-colored-text-in-java-console/
	private static final String[] COLORS = { "\u001B[31m", "\u001B[32m", "\u001B[33m", "\u001B[34m", "\u001B[35m",
			"\u001B[36m", };

	/**
	 * Took from
	 * https://www.geeksforgeeks.org/how-to-print-colored-text-in-java-console
	 * Prints the given text to the console in a randomly selected color. This
	 * method selects a color from a predefined array of color codes (assumed to be
	 * named COLORS) and prints the text in that color. The color reset code
	 * "\u001B[0m" is appended to reset the console color after printing the text.
	 *
	 * @param text The string to be printed in a random color.
	 */
	public static void printInRandomColor(String text) {
		Random rand = new Random();
		int randomColorIndex = rand.nextInt(COLORS.length);
		// Print the text in the randomly selected color and then reset the color
		System.out.println(COLORS[randomColorIndex] + text + "\u001B[0m");
	}

	/**
	 * Initiates the Trie-based similarity search application. This method starts by
	 * validating command line arguments for the file path, then reads input from
	 * the user to perform various operations like insert, search, and find similar
	 * words in the Trie. It also handles the application menu and exit option.
	 *
	 * @param trie The TrieBasedSimilaritySearch instance to operate on.
	 * @param args Command line arguments, expected to contain the file path at the
	 *             first index.
	 * @throws IllegalArgumentException If no file path is provided in the command
	 *                                  line arguments.
	 */
	public void startnew(TrieBasedSimilaritySearch trie, String[] args) {
		if (args.length == 0)
			throw new IllegalArgumentException("File path cannot be null. Add path to your command line.");

		Path path = Paths.get(args[0]);
		Scanner inputScanner = new Scanner(System.in);

		if (insertAllFromFile(path, trie))
			printInRandomColor("Words from " + path.getFileName() + " are inserted to the Trie.");

		try {
			while (true) {
				printMenu();
				String option = inputScanner.nextLine();

				switch (option) {
				case "1":
					handleInsert(inputScanner, path, trie);
					break;
				case "2":
					handleSearch(inputScanner, trie);
					break;
				case "3":
					handleFindSimilarWords(inputScanner, path, trie);
					break;
				case "4":
					printInRandomColor("Exiting the program...");
					return;
				default:
					printInRandomColor("Invalid option. Please try again.");
				}
			}
		} finally {
			inputScanner.close();
		}
	}

	/**
	 * Prints the menu options for the application in a randomly selected color. The
	 * options include insert, search, find similar words, and exit.
	 */
	private void printMenu() {
		printInRandomColor("Please choose an option:\n1. Insert\n2. Search\n3. Find Similar Words\n4. Exit");
	}

	// Helpers
	/**
	 * Handles the insert operation in the Trie. Prompts the user to enter a word to
	 * insert. If the word is not already present in the Trie, it inserts the word
	 * and writes it to the file.
	 *
	 * @param scanner Scanner to read user input.
	 * @param path    The file path where the word is to be written.
	 * @param trie    The TrieBasedSimilaritySearch instance to operate on.
	 */
	private void handleInsert(Scanner scanner, Path path, TrieBasedSimilaritySearch trie) {
		printInRandomColor("Enter a word to insert:");
		String word = scanner.nextLine();

		if (trie.search(word)) {
			printInRandomColor("The word \"" + word + "\" exists in the Trie.");
			return;
		}
		trie.insert(word);
		writeWordToFile(path, word);
		printInRandomColor("The word \"" + word + "\" has been inserted into the Trie.");
	}

	/**
	 * Handles the search operation in the Trie. Prompts the user to enter a word to
	 * search. It then checks if the word exists in the Trie and informs the user of
	 * the result.
	 *
	 * @param scanner Scanner to read user input.
	 * @param trie    The TrieBasedSimilaritySearch instance to operate on.
	 */
	private void handleSearch(Scanner scanner, TrieBasedSimilaritySearch trie) {
		printInRandomColor("Enter a word to search:");
		String word = scanner.nextLine();

		if (trie.search(word)) {
			printInRandomColor("The word \"" + word + "\" exists in the Trie.");
		} else {
			printInRandomColor("The word \"" + word + "\" does not exist in the Trie.");
		}
	}

	/**
	 * Handles the operation to find similar words in the Trie. Prompts the user to
	 * enter a word and an edit distance threshold. It then finds and displays words
	 * in the Trie that are similar to the entered word within the specified edit
	 * distance.
	 *
	 * @param scanner Scanner to read user input.
	 * @param path    The file path for potential use.
	 * @param trie    The TrieBasedSimilaritySearch instance to operate on.
	 */
	private void handleFindSimilarWords(Scanner scanner, Path path, TrieBasedSimilaritySearch trie) {
		printInRandomColor("Enter a word to find similar words:");
		String word = scanner.nextLine();
		printInRandomColor("Enter the edit distance threshold:");
		int threshold = Integer.parseInt(scanner.nextLine());
		if (threshold < 0) {
			System.err.println("less than 0 not valid");
			return;
		}

		Set<String> similarWords = trie.findSimilarWords(word, threshold);

		if (threshold >= word.length() || threshold == 0) {
			if (threshold == 0) {
				System.out.println(word);
				return;
			}
			System.out.println("Cant give edit distance more than or equal to word length");

			threshold = word.length() - 1;
			System.out.println("Setting maximum thrushhold as : " + threshold);

			similarWords = trie.findSimilarWords(word, threshold);

		}
		List<String> similarWordsList = new ArrayList<>(similarWords);
		Collections.sort(similarWordsList);
		if (similarWords.isEmpty()) {
			printInRandomColor("No similar words found for \"" + word + "\" within edit distance of " + threshold);
		} else {
			printInRandomColor("Similar words found:");
			for (String similarWord : similarWordsList) {

				printInRandomColor(similarWord);
				
			}
		}


	}

	/**
	 * The main entry point of the TrieBasedSimilaritySearch application. This
	 * method initializes a new instance of TrieBasedSimilaritySearch and begins the
	 * application process by calling the 'startnew' method, passing the instance
	 * and the command line arguments.
	 *
	 * @param args The command line arguments passed to the application, expected to
	 *             contain necessary parameters (file path) for Trie operations.
	 */
	public static void main(String[] args) {
		TrieBasedSimilaritySearch trie = new TrieBasedSimilaritySearch();

		trie.startnew(trie, args);
		

	}



}
