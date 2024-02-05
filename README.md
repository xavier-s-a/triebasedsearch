# CompSt 751 Group Project

## Name


+ Trie-based similarity search

## People


| Name    | Git Username | UWM Email     |
| :---:        |    :----:   |        :---: |
|Aaron Michelson |@aaronmichelson4 |am2@uwm.edu|
|Sheetal Saju | @sheetal-saju |ssaju@uwm.edu|
|Sowmiya Subramanian|@SowmiyaSub|sowmiya@uwm.edu|
|Sreeteja Tummala | @sreetejaa | tummalas@uwm.edu|
| Xavier Sebastian      | @xavier-s-a       | xadettu@uwm.edu  |



## Paper

+ [Trie-based similarity search and join](https://dl.acm.org/doi/10.1145/2457317.2457389)


### Scope

In this paper the author addresses the concern of efficiently filtering the duplicate records. Hence the author introduces a trie-based system to implement string similarity search and join, over large-scale datasets. 
In a String Similarity Query, database strings that are similar to the data set is matched. Reasons for using approximate rather than exact matching in queries are mainly due to the allowance of typographical errors, or existence of multiple conventions for the same entity such as names or addresses. 
Description on the queries used:
•	Trie-base data structure: The identified datasets are indexed to trie. Duplicate strings are indexed to the same path and different strings are stored in the leaf node of the path. The advantages of using a trie-based system: 
-	Comparatively occupies smaller amount of space than strings. 
-	Captures the prefix-sharing property of strings and hence is able to share computation. 
-	Can be easily extended to support updates.
-	Does not need final verification.

•	String Similarity Search: A String Similarity Search algorithm is used to match database strings that are similar. In this method we traverse through the trie, starting from the root and compute the active node set of each prefix of the query string. After the computation of each active node set,  filtering methods such as length filtering are used to further reduce the size of active node set.

## Plan

### Main classes

What we are going to implement from Trie-based Similarity Search and Join
•	Trie-bases data structure 
•	Similarity search algorithm
High-level overview of how can perform string similarity search in a trie:
•	Build a Trie: First, we need to build a trie data structure that contains the set of strings we want to search within. (Note: Each node in the trie represents a character, and paths from the root to leaf nodes form complete strings.) 

•	Similarity Metric: We will be using Edit distance metrics for doing similarity search. (Note: Edit Distance measures the number of edits (insertions, deletions, substitutions) required to transform one string into another. Lower edit distances imply higher similarity.) 


•	Search for Similar Strings Algorithm:
-	To find similar strings for a given query string, we first start from the root node of the trie, incrementally Compute Active node set of each prefix of the Query string. 
-	Then apply filtering methods like length filtering single branch filtering to get active node set of complete query string.
-	Finally, we check for all the nodes in the active node set that is a leaf node and add to the result set.

Technique Details :
Compute Active Node :
•	Traversal with Pruning: 
-	Traverse through the nodes and calculate the edit distance between the query string and the string represented by each node. 
-	Compare the calculated edit distance with the defined threshold. If the edit distance exceeds the threshold, prune that node and its descendants from further consideration.

•	Adding Active Nodes: If the edit distance is within the threshold, mark the node as an active node and add it the set.

Filtering:
•	Length Filtering: The basic idea is that after computing one active node set, check all the active nodes in the set to see if there is any node whose descendant leaf nodes are all have length difference lager than with the query string; if so, these nodes should be removed front the active node set

•	Single branch filtering: If two nodes in an active node set belong to the same trie branch and they have exactly the same set of descendant leaf nodes, then one of them can be pruned.

### Helper classes

Are there any helpers you need to implement?
These can be from other open source projects, as long as you credit them...

For the base implementation of Project Trie there is one helper class and two helper methods that have been implemented.

• The method 'private boolean cycle(TrieNode r, Set<TrieNode> set)' is implemented as a helper method for the invariant to check the cycle.

• The method 'private boolean checkEndOfWord(TrieNode r)' is implemented as a helper method for the invariant to check if the isEndOfWord status is correct.

• The class 'public static class Spy' is implemented as a helper class for internal testing with the wellFormed() method.

### Test Suites

#### Primary Unit Tests

1.	Basic Functionality and Case Sensitivity (Tests 01-05, 16): Tests the functionality of common trie operations like word search and insertion, with an emphasis on managing case sensitivity and queries for non-existent words.
2.	Error Handling and Validation (Tests 03-04, 11-13, 17-21, 25, 28, 40-41, 50): Ensures proper error handling by evaluating the trie's reaction to invalid inputs, such as spaces, capital letters, special characters, and null values.
3.	Edge Cases and Partial Matches (Tests 06-10, 22, 24, 26-27, 29): Assesses how well the trie handles edge cases, such as reversed strings, lengthy strings, and partial word matches, in order to avoid producing false positive results.
4.	Alphabet and Length Handling (Tests 14-15, 20, 23): Confirms that the trie can handle any word length, from single characters to very long words, and any alphabetic character.
5.	Similarity Search Functionality (Tests 30-39, 42-48, 49, 51-59, 73-74): Evaluates the trie's ability to identify words that are similar based on edit distance across a range of word lengths and edit distances.
6.	Advanced Similarity Search and Long Words (Tests 62-69, 70-72): Tests the trie's resilience in challenging searches by concentrating on sophisticated similarity search scenarios, particularly those involving long words and large edit distances.

#### Data Structure Well-formed-ness Tests

In data structure well-formedness tests, the following aspects are examined:

  1. Whether the size of the children array is 26 for all nodes in the data structure.
  2. The status of 'isEndOfWord' is verified. It should be true if the node does not contain any child nodes.
  3. Checking for cycles in the data structure.
  4. The root node should not be null.
  5. Checks whether child Node has multiple parents.

#### Efficiency Tests

The efficiency test checks for time complexity for:

  1. Insertion of words.
  2. Searching for words.
  3. Finding similar words.

#### Random Tests

The random test checks the ADT against a reference implementation that satisfies the functional properties.  The reference implementation includes random testing for the insert, search and findSimilarWordsmethods, and all random testing is able to pass.

## Status

**12/17/2023**

The final project for Trie-based Similarity Search was submitted on 12/17/2023 and includes data structure well-formed testing, primary unit testing, efficiency testing and random testing.  All of the aforementioned testing passes with no errors or failures. 

## Resources

The homework assignments created by John Boyland for the Computer Studies 751 course taught in Fall 2023 semester at the University of Wisconsin - Milwaukee were used as as resource for implementing the code for Project Trie.  This code was used as a resource for the Base Implementation, as well as the various tests (Primary Unit Tests, Data Structure Well-formed-ness Tests, Efficiency Tests, Random Tests) that were implemented for the Project Trie.
