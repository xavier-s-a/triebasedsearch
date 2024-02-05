import junit.framework.TestCase;

import edu.uwm.cs751.TrieBasedSimilaritySearch;

import java.util.*;

public class PrimaryUnitTest extends TestCase {
	
	private TrieBasedSimilaritySearch trie;
	protected void assertException(Class<? extends Throwable> c, Runnable r) {
		try {
			r.run();
			assertFalse("Exception should have been thrown",true);
		} catch (Throwable ex) {
			assertTrue("should throw exception of " + c + ", not of " + ex.getClass(), c.isInstance(ex));
		}
	}
	   
    public void setUp() {
        trie = new TrieBasedSimilaritySearch();
    }

 // Insert and search for "banana".
    public void test01() {
        trie.insert("banana");
        assertTrue(trie.search("banana"));
    }

 // Insert and search for "cherry" and "mango".
    public void test02() {
        trie.insert("cherry");
        trie.insert("mango");
        assertTrue(trie.search("cherry"));
        assertTrue(trie.search("mango"));
    }

 // Ensure that inserting "Apple" (with uppercase) throws an exception and searching for "apple" (lowercase) returns false.
    public void test03() {
    	assertException(IllegalArgumentException.class, () -> trie.insert("Apple"));
        assertFalse(trie.search("apple"));

    }

 // Insert "apple" and ensure searching for "Apple" (uppercase) throws an exception.
    public void test04() {
        trie.insert("apple");
        assertException(IllegalArgumentException.class, () -> trie.search("Apple"));
    }

 // Confirm that searching for "grape" (not inserted) returns false.
    public void test05() {
        assertFalse(trie.search("grape"));
    }

 // Insert "blueberry" and confirm searching for "blue" returns false.
    public void test06() {
        trie.insert("blueberry");
        assertFalse(trie.search("blue"));
    }

 // Insert "rasp" and confirm that "raspberry" is not found.
    public void test07() {
        trie.insert("rasp");
        assertFalse(trie.search("raspberry"));
    }

 // Insert "rasp" and "raspberry"; search for both and check that partials "ras" and "raspb" are not found.
    public void test08() {
        trie.insert("rasp");
        trie.insert("raspberry");
        assertTrue(trie.search("rasp"));
        assertTrue(trie.search("raspberry"));
        assertFalse(trie.search("ras"));
        assertFalse( trie.search("raspb"));
    }
    
 // Insert "raspberry" and "rasp"; search for both and confirm that partials "ras" and "raspb" are not found.
    public void test09() {
        trie.insert("raspberry");
        trie.insert("rasp");
        assertTrue(trie.search("raspberry"));
        assertTrue(trie.search("rasp"));
        assertFalse(trie.search("ras"));
        assertFalse(trie.search("raspb"));
    }
    
 // Insert "multitestone" and "multitesttwo"; confirm that "multi" is not found.
    public void test10() {
        trie.insert("multitestone");
        trie.insert("multitesttwo");
        assertFalse(trie.search("multi"));
    }
    
 // Ensure that inserting "abc123" (invalid input) throws an exception.
    public void test11() {

    	assertException(IllegalArgumentException.class, () -> trie.insert("abc123"));

    }

 // Ensure that inserting "world!" (invalid input) throws an exception.
    public void test12() {
        assertException(IllegalArgumentException.class, () -> trie.insert("world!"));
    }
    
 // Insert "hello" and ensure searching for "hello!" (invalid input) throws an exception.
    public void test13() {
        trie.insert("hello");
        assertException(IllegalArgumentException.class, () -> trie.search("hello!"));
    }
    
 // Confirm that searching for a space character " " returns false.
    public void test14() {
      
    	assertFalse(trie.search(" "));
        
        
    }

 // Insert and search for a space character " ".
    public void test15() {
   
        assertTrue(trie.insert(" "));
        assertTrue(trie.search(" "));
        
    }
    
 // Insert "strawberry" and check that searching for "strAWBeRRy" (case-sensitive search) throws an exception.
    public void test16() {

         trie.insert("strawberry");
        assertException(IllegalArgumentException.class,() ->trie.search("strAWBeRRy"));   

    }
	    
 // Insert invalid input "co-operate" expecting an exception.
    public void test17() {
        assertException(IllegalArgumentException.class, () -> trie.insert("co-operate"));
    }

    // Insert "duplicate" and confirm that inserting it again returns false.
    public void test18() {
        trie.insert("duplicate");
        assertFalse(trie.insert("duplicate"));
    }

    // Ensure that inserting `null` throws a NullPointerException.
    public void test19() {
        assertException(NullPointerException.class, () -> trie.insert(null));
    }

    // Check searching for `null` throws a NullPointerException.
    public void test20() {
        assertException(NullPointerException.class, () -> trie.search(null));
    }
    
 // Inserting/Searching a string with non-alphabetic characters "123!@#" should throw an IllegalArgumentException.
    public void test21() {
    	assertException(IllegalArgumentException.class, () -> trie.insert("123!@#"));
        assertException(IllegalArgumentException.class, () -> trie.search("123!@#"));
    }
    
 // Check searching for a non-existent string "nonexistent" should return false.
    public void test22() {
        assertFalse(trie.search("nonexistent"));
    }

    // Ensure each alphabet character can be inserted successfully.
    public void test23() {
        for (char ch = 'a'; ch <= 'z'; ch++) {
            assertTrue(trie.insert(String.valueOf(ch)));
        }
    }

    // Searching for a string with leading space should throw an IllegalArgumentException.
    public void test24() {
        assertException(IllegalArgumentException.class, () -> trie.search(" space "));
    }

    // Inserting strings with leading or trailing spaces should throw an IllegalArgumentException.
    public void test25() {
        assertException(IllegalArgumentException.class, () -> trie.insert(" leading"));
        assertException(IllegalArgumentException.class, () -> trie.insert("trailing "));
    }

    // Inserting a very long string of 'a' characters and confirming it can be searched.
    public void test26() {
        String longWord = new String(new char[1000]).replace("\0", "a");
        assertTrue(trie.insert(longWord));
    }

    // Confirming a very long string of 'a' characters is searchable after insertion.
    public void test27() {
        String longWord = new String(new char[1000]).replace("\0", "a");
        trie.insert(longWord);
        assertTrue(trie.search(longWord));
    }

    // Inserting a string with a special character "café" should throw an IllegalArgumentException.
    public void test28() {
        assertException(IllegalArgumentException.class, () -> trie.insert("café"));
    }
    
    // Verify that searching for a reversed string "palindrome" returns false.
    public void test29() {
        trie.insert("palindrome");
        assertFalse(trie.search(new StringBuilder("palindrome").reverse().toString()));
    }

    // Check that similar words within an edit distance of 1 for "tests" include "test".
    public void test30() {
        trie.insert("test");
        Set<String> result = trie.findSimilarWords("tests", 1);
        assertTrue(result.contains("test"));
    }

    // Ensure that "hello" is not found as a similar word to "world" within an edit distance of 1.
    public void test31() {
        trie.insert("hello");
        Set<String> result = trie.findSimilarWords("world", 1);
        assertFalse(result.contains("hello"));
    }

    // Confirm finding "distance" as a similar word to "dist" within an edit distance of 4.
    public void test32() {
        trie.insert("distance");
        Set<String> result = trie.findSimilarWords("dist", 4);
        assertTrue(result.contains("distance"));
    }

    // Verify IllegalArgumentException is thrown for negative edit distance in findSimilarWords.
    public void test33() {
        trie.insert("example");
        assertException(IllegalArgumentException.class, () -> trie.findSimilarWords("example", -1));
    }

    // Check that finding similar words to "word" with an edit distance of 0 only includes "word".
    public void test34() {
        trie.insert("word");
        Set<String> result = trie.findSimilarWords("word", 0);
        assertEquals(1, result.size());
        assertTrue(result.contains("word"));
    }

    // Ensure no similar words are found for "absent" with an edit distance of 0.
    public void test35() {
        Set<String> result = trie.findSimilarWords("absent", 0);
        assertTrue(result.isEmpty());
    }

    // Confirm finding both "word" and "words" as similar words to "word" within an edit distance of 1.
    public void test36() {
        trie.insert("word");
        trie.insert("words");
        Set<String> result = trie.findSimilarWords("word", 1);
        assertTrue(result.contains("word") && result.contains("words"));
    }

    // Verify finding both "hello" and "help" as similar words to "hell" within an edit distance of 2.
    public void test37() {
        trie.insert("hello");
        trie.insert("help");
        Set<String> result = trie.findSimilarWords("hell", 2);
        assertTrue(result.contains("hello") && result.contains("help"));
    }

    // Confirm finding "apple" as a similar word to "aple" within an edit distance of 1.
    public void test38() {
        trie.insert("apple");
        Set<String> result = trie.findSimilarWords("aple", 1);
        assertTrue(result.contains("apple"));
    }

    // Verify finding "example" as a similar word to "examp" within an edit distance of 3.
    public void test39() {
        trie.insert("example");
        trie.insert("samples");
        Set<String> result = trie.findSimilarWords("examp", 3);
        assertTrue(result.contains("example"));
    }

    // Ensure IllegalArgumentException is thrown for attempting to insert strings with leading/trailing spaces.
    public void test40() {
        assertException(IllegalArgumentException.class, () -> trie.insert("space "));
        assertException(IllegalArgumentException.class, () -> trie.insert(" space"));
    }

    // Verify that searching strings with leading/trailing spaces throws IllegalArgumentException.
    public void test41() {
        assertException(IllegalArgumentException.class, () -> trie.search("space "));
        assertException(IllegalArgumentException.class, () -> trie.search(" space"));
    }

    // Confirm finding "example" and "sample" as similar words to "example" within an edit distance of 2.
    public void test42() {
        trie.insert("example");
        trie.insert("exemplary");
        trie.insert("sample");
        Set<String> result = trie.findSimilarWords("example", 2);
        assertTrue(result.contains("example") && result.contains("sample"));
    }

    // Check finding "quack" and "quick" as similar words to "quack" within an edit distance of 3.
    public void test43() {
        trie.insert("quick");
        trie.insert("quack");
        trie.insert("quicksilver");
        Set<String> result = trie.findSimilarWords("quack", 3);
        assertTrue(result.contains("quack") && result.contains("quick"));
        assertFalse(result.contains("quicksilver"));
    }

    // Verify finding "test" and "tent" as similar words to "test" within an edit distance of 1.
    public void test44() {
        trie.insert("test");
        trie.insert("tent");
        trie.insert("taste");
        Set<String> result = trie.findSimilarWords("test", 1);
        System.out.println(result);
        assertTrue(result.contains("test") && result.contains("tent"));
        assertFalse(result.contains("taste"));
    }

    // Confirm finding "apple" and "apply" as similar words to "aple" within an edit distance of 2.
    public void test45() {
        trie.insert("apple");
        trie.insert("apply");
        Set<String> result = trie.findSimilarWords("aple", 2);
        assertTrue(result.contains("apple") && result.contains("apply"));
    }

    // Check finding "hello" and "hell" as similar words to "hallo" within an edit distance of 2.
    public void test46() {
        trie.insert("hello");
        trie.insert("hell");
        Set<String> result = trie.findSimilarWords("hallo", 2);
        assertTrue(result.contains("hello") && result.contains("hell"));
    }

    // Verify finding "node" and "mode" as similar words to "no" within an edit distance of 3.
    public void test47() {
        trie.insert("node");
        trie.insert("mode");
        Set<String> result = trie.findSimilarWords("no", 3);
        assertTrue(result.contains("node") && result.contains("mode"));
    }

    // Confirm finding "distance" and "instance" as similar words to "stance" within an edit distance of 3.
    public void test48() {
        trie.insert("distance");
        trie.insert("instance");
        Set<String> result = trie.findSimilarWords("stance", 3);
        assertTrue(result.contains("distance") && result.contains("instance"));
    }

 // Verify finding "example" and "examine" as similar words to an empty string within an edit distance of 7.
    public void test49() {
        trie.insert("example");
        trie.insert("examine");
        Set<String> result = trie.findSimilarWords("", 7);
        assertTrue(result.contains("example") && result.contains("examine"));
    }

    // Ensure NullPointerException is thrown for null input in findSimilarWords method.
    public void test50() {
        assertException(NullPointerException.class, () -> trie.findSimilarWords(null, 1));
    }

    // Confirm finding "word" as a similar word to itself even with a high edit distance of 10.
    public void test51() {
        trie.insert("word");
        Set<String> result = trie.findSimilarWords("word", 10);
        assertTrue(result.contains("word"));
    }

    // Check for all similar words to "cat" within an edit distance of 1.
    public void test52() {
        trie.insert("cat");
        trie.insert("bat");
        trie.insert("rat");
        trie.insert("hat");
        Set<String> result = trie.findSimilarWords("cat", 1);
        assertEquals(4, result.size());
        assertTrue(result.containsAll(Arrays.asList("bat", "rat", "hat")));
    }

    // Verify finding similar words to "star" within an edit distance of 2.
    public void test53() {
        trie.insert("star");
        trie.insert("scar");
        trie.insert("spar");
        trie.insert("car");
        Set<String> result = trie.findSimilarWords("star", 2);
        assertEquals(4, result.size());
        assertTrue(result.containsAll(Arrays.asList("scar", "spar", "car")));
    }

    // Check for similar words to "talk" within an edit distance of 3.
    public void test54() {
        trie.insert("talk");
        trie.insert("walk");
        trie.insert("balk");
        trie.insert("hawk");
        Set<String> result = trie.findSimilarWords("talk", 3);
        assertEquals(4, result.size());
        assertTrue(result.containsAll(Arrays.asList("walk", "balk", "hawk")));
    }

    // Confirm finding similar words to "wind" within an edit distance of 1.
    public void test55() {
        trie.insert("wind");
        trie.insert("wine");
        trie.insert("wink");
        trie.insert("wing");
        Set<String> result = trie.findSimilarWords("wind", 1);
        assertEquals(4, result.size());
    }

    // Verify finding similar words to "bead" within an edit distance of 1.
    public void test56() {
        trie.insert("beam");
        trie.insert("bear");
        trie.insert("bean");
        trie.insert("beat");
        Set<String> result = trie.findSimilarWords("bead", 1);
        assertEquals(4, result.size());
        assertTrue(result.containsAll(Arrays.asList("beam", "bear", "bean")));
    }

    // Check for similar words to "right" within an edit distance of 2.
    public void test57() {
        trie.insert("light");
        trie.insert("might");
        trie.insert("night");
        trie.insert("sight");
        Set<String> result = trie.findSimilarWords("right", 2);
        assertEquals(4, result.size());
        assertTrue(result.containsAll(Arrays.asList("light", "might", "night", "sight")));
    }

    // Confirm finding similar words to "slow" within an edit distance of 2.
    public void test58() {
        trie.insert("flow");
        trie.insert("flew");
        trie.insert("flaw");
        Set<String> result = trie.findSimilarWords("slow", 2);
        assertEquals(3, result.size());
        assertTrue(result.containsAll(Arrays.asList("flow", "flew", "flaw")));
    }

    // Verify finding similar words to "grace" within an edit distance of 2.
    public void test59() {
        trie.insert("great");
        trie.insert("grate");
        trie.insert("grape");
        trie.insert("grade");
        Set<String> result = trie.findSimilarWords("grace", 2);
        assertEquals(3, result.size());
        assertTrue(result.containsAll(Arrays.asList( "grate", "grape", "grade")));
    }

    // Check for similar words to "boon" within an edit distance of 1.
    public void test60() {
        trie.insert("moon");
        trie.insert("soon");
        trie.insert("goon");
        Set<String> result = trie.findSimilarWords("boon", 1);
        assertEquals(3, result.size());
        assertTrue(result.containsAll(Arrays.asList("moon", "soon", "goon")));
    }

    // Confirm finding similar words to "dazzle" within an edit distance of 4.
    public void test61() {
        trie.insert("jazz");
        trie.insert("buzz");
        trie.insert("fuzz");
        Set<String> result = trie.findSimilarWords("dazzle", 4);
        assertEquals(3, result.size());
        assertTrue(result.containsAll(Arrays.asList("jazz", "buzz", "fuzz")));
    }
    
    // Test finding similar long words to "supercalifragilisticexpialidocious" within a high edit distance.
    public void test62() {
        trie.insert("supercalifragilisticexpialidocious");
        trie.insert("supercalifragilisticexpialidociously");
        trie.insert("supercalifragilisticexpialidocia");
        Set<String> result = trie.findSimilarWords("supercalifragilisticexpialidocious", 5);
        assertEquals(3, result.size());
        assertTrue(result.containsAll(Arrays.asList("supercalifragilisticexpialidocious", "supercalifragilisticexpialidociously", "supercalifragilisticexpialidocia")));
    }

    // Test finding similar long words with a common long prefix.
    public void test63() {
        trie.insert("uncharacteristically");
        trie.insert("uncharacteristicallylongword");
        trie.insert("uncharacteristic");
        Set<String> result = trie.findSimilarWords("uncharacteristically", 6);
        assertEquals(2, result.size());
        assertTrue(result.containsAll(Arrays.asList("uncharacteristically", "uncharacteristic")));
    }

    // Test finding similar long words with a high edit distance.
    public void test64() {
        trie.insert("pneumonoultramicroscopicsilicovolcanoconiosis");
        trie.insert("pneumonoultramicroscopicsilicovolcanoconiosises");
        trie.insert("pneumonoultramicroscopicsilicovolcanoconios");
        Set<String> result = trie.findSimilarWords("pneumonoultramicroscopicsilicovolcanoconiosis", 10);
        assertEquals(3, result.size());
        assertTrue(result.containsAll(Arrays.asList("pneumonoultramicroscopicsilicovolcanoconiosis", "pneumonoultramicroscopicsilicovolcanoconiosises", "pneumonoultramicroscopicsilicovolcanoconios")));
    }
    
    // Test finding similar long words with minimal edit distance.
    public void test65() {
        trie.insert("antidisestablishmentarianism");
        trie.insert("antidisestablishmentarian");
        trie.insert("antidisestablishmentarianisms");
        Set<String> result = trie.findSimilarWords("antidisestablishmentarianism", 2);
        assertEquals(2, result.size());
        assertTrue(result.containsAll(Arrays.asList("antidisestablishmentarianism", "antidisestablishmentarianisms")));
    }
    
    // Test finding similar long words with varying edit distances.
    public void test66() {
        trie.insert("floccinaucinihilipilification");
        trie.insert("floccinaucinihilipilificational");
        trie.insert("floccinaucinihilipilificating");
        Set<String> result = trie.findSimilarWords("floccinaucinihilipilification", 7);
        assertEquals(3, result.size());
        assertTrue(result.containsAll(Arrays.asList("floccinaucinihilipilification", "floccinaucinihilipilificational", "floccinaucinihilipilificating")));
    }
    

    // Test finding no similar long words within a low edit distance.
    public void test67() {
        trie.insert("hippopotomonstrosesquippedaliophobia");
        trie.insert("hippopotomonstrosesquippedaliophobias");
        trie.insert("hippopotomonstrosesquippedaliophobic");
        Set<String> result = trie.findSimilarWords("hippopotomonstrosesquippedaliophobia", 1);
        assertEquals(3, result.size());
        assertTrue(result.containsAll(Arrays.asList("hippopotomonstrosesquippedaliophobia", "hippopotomonstrosesquippedaliophobias", "hippopotomonstrosesquippedaliophobic")));
    }
    
    // Test finding similar long words created by repeating a single character 'b'.
    public void test68() {
        String longWordB = new String(new char[800]).replace("\0", "b");
        String modifiedLongWordB = new String(new char[799]).replace("\0", "b") + "c";
        trie.insert(longWordB);
        trie.insert(modifiedLongWordB);
        Set<String> result = trie.findSimilarWords(longWordB, 2);
        assertEquals(2, result.size());
        assertTrue(result.contains(longWordB) && result.contains(modifiedLongWordB));
    }
    
    // Test finding similar long words created by repeating a single character 'a'.
    public void test69() {
        String longWord = new String(new char[1000]).replace("\0", "a");
        String slightlyDifferentLongWord = new String(new char[999]).replace("\0", "a") + "b";
        trie.insert(longWord);
        trie.insert(slightlyDifferentLongWord);
        Set<String> result = trie.findSimilarWords(longWord, 1);
        assertEquals(2, result.size());
        assertTrue(result.contains(longWord) && result.contains(slightlyDifferentLongWord));
    }

    // Test findSimilarWords method with an empty string and zero edit distance, expecting no similar words found in the Trie.
    public void test70() {
        trie.insert("test");
        trie.insert("hello");
        Set<String> result = trie.findSimilarWords(" ", 0);
        assertTrue(result.isEmpty());
    }
    
    // Test findSimilarWords method with a short target word and a large edit distance, expecting to find various length words including very long ones.
    public void test71() {
        trie.insert("short");
        trie.insert("longerword");
        trie.insert("extremelylongwordthatexceedsnormal");
        Set<String> result = trie.findSimilarWords("short", 50);
        assertEquals(3, result.size());
        assertTrue(result.containsAll(Arrays.asList("short", "longerword", "extremelylongwordthatexceedsnormal")));
    }
    
    // Test findSimilarWords with a non-existent target word and a moderate edit distance, expecting to find no similar words in the Trie.
    public void test72() {
        trie.insert("existingword");
        trie.insert("anotherword");
        Set<String> result = trie.findSimilarWords("nonexistentword", 3);
        assertTrue(result.isEmpty());
    }
    
    // Test finding similar words for an empty string with a high edit distance, expecting multiple matches.
    public void test73() {
        trie.insert("a");
        trie.insert("ab");
        trie.insert("abc");
        Set<String> result = trie.findSimilarWords(" ", 3);
        assertEquals(3, result.size());
        assertTrue(result.containsAll(Arrays.asList("a", "ab", "abc")));
    }
    
    // Test finding similar words for a very long word with a high edit distance, expecting no matches due to the uniqueness of the word.
    public void test74() {
        String veryLongWord = new String(new char[1000]).replace("\0", "f");
        trie.insert(veryLongWord);
        Set<String> result = trie.findSimilarWords(veryLongWord, 100);
        

        assertEquals(1, result.size());
        assertTrue(result.contains(veryLongWord));
    }
 //test for longer words greater than 40 length using optmized edit ditance 
    public void test75() {
       
        trie.insert("extremelylongwordthatexceedsnormalextremelylongwordthatexceedsnormal");
        trie.insert("abcdefdextremelylongwordthatexceedsnormalextremelylongwordthatexceedsnormal");

        Set<String> result = trie.findSimilarWords("extremelylongwordthatexceedsnormalextremelylongwordthatexceedsnormal", 7);
        assertEquals(2, result.size());
        assertTrue(result.containsAll(Arrays.asList( "abcdefdextremelylongwordthatexceedsnormalextremelylongwordthatexceedsnormal")));
    }
    
    public void test76() {
        String word = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcd";
        trie.insert(word);
        Set<String> result = trie.findSimilarWords(word, 0);
        assertEquals(1, result.size());
        assertTrue(result.contains(word));
    }

   
    public void test77() {
        String word = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcd";
        String similarWord = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxybacd";
        trie.insert(word);
        Set<String> result = trie.findSimilarWords(similarWord, 1);
        assertTrue(result.contains(word));
    }

   
 
    
    public void test78() {
        String word1 = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcd";
        String word2 = "abcdefghijklmnopqrstuvwxyzaabcdefghijklmnopqrstuvwxyz";
        trie.insert(word1);
        trie.insert(word2);
        Set<String> result = trie.findSimilarWords(word1, 2);
        assertEquals(2, result.size());
        assertTrue(result.containsAll(Arrays.asList(word1, word2)));
    }

 

    
  
    
   
  
    
}
