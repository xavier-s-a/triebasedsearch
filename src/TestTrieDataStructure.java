import edu.uwm.cs751.TrieBasedSimilaritySearch;
import edu.uwm.cs751.TrieBasedSimilaritySearch.Spy.Node;
import junit.framework.TestCase;

public class TestTrieDataStructure extends TestCase {
	// Test invariant
	TrieBasedSimilaritySearch.Spy spy = new TrieBasedSimilaritySearch.Spy();

	protected TrieBasedSimilaritySearch t;
	
	// node null
	public void test00() {
		t = spy.create(null);
		assertFalse(spy.wellFormed(t));
	}

	public void test01() {
		Node n = spy.newNode(null, true);
		t = spy.create(n);
		assertFalse(spy.wellFormed(t));
	}

	public void test02() {
		Node n = spy.newNode(new Node[26], false);
		Node n2 = spy.newNode(null, true);
		spy.setNext(n, 0, n2);
		t = spy.create(n);
		assertFalse(spy.wellFormed(t));
	}

	public void test03() {
		Node n = spy.newNode(new Node[26], false);
		Node n2 = spy.newNode(null, false);
		spy.setNext(n, 24, n2);
		t = spy.create(n);
		assertFalse(spy.wellFormed(t));
	}
	
	public void test04() {
		Node n = spy.newNode(new Node[26], false);
		Node n2 = spy.newNode(null, true);
		Node n3 = spy.newNode(new Node[26], true);
		Node n4 = spy.newNode(new Node[26], true);
		spy.setNext(n, 2, n3);
		spy.setNext(n, 4, n4);
		spy.setNext(n, 24, n2);
		t = spy.create(n);
		assertFalse(spy.wellFormed(t));
	}
	
	public void test05() {
		Node n = spy.newNode(new Node[26], false);
		Node n2 = spy.newNode(new Node[26], true);
		Node n3 = spy.newNode(new Node[26], true);
		Node n4 = spy.newNode(new Node[26], true);
		spy.setNext(n, 2, n3);
		spy.setNext(n, 4, n4);
		spy.setNext(n, 24, n2);
		t = spy.create(n);
		assertTrue(spy.wellFormed(t));
	}
	
	public void test06() {
		Node n = spy.newNode(new Node[26], false);
		Node n2 = spy.newNode(null, true);
		Node n3 = spy.newNode(new Node[26], true);
		Node n4 = spy.newNode(new Node[26], true);
		spy.setNext(n, 2, n3);
		spy.setNext(n3, 4, n4);
		spy.setNext(n4, 24, n2);
		t = spy.create(n);
		assertFalse(spy.wellFormed(t));
	}
	
	public void test07() {
		Node n = spy.newNode(new Node[26], false);
		Node n2 = spy.newNode(new Node[26], true);
		Node n3 = spy.newNode(new Node[26], true);
		Node n4 = spy.newNode(new Node[26], true);
		spy.setNext(n, 2, n3);
		spy.setNext(n3, 4, n4);
		spy.setNext(n4, 24, n2);
		t = spy.create(n);
		assertTrue(spy.wellFormed(t));
	}
	
	public void test08() {
		Node n = spy.newNode(new Node[26], false);
		Node n2 = spy.newNode(new Node[26], true);
		Node n3 = spy.newNode(new Node[26], false);
		Node n4 = spy.newNode(new Node[26], false);
		Node n5 = spy.newNode(new Node[26], true);
		spy.setNext(n, 2, n3);
		spy.setNext(n3, 4, n4);
		spy.setNext(n4, 24, n2);
		spy.setNext(n4, 4, n5);
		t = spy.create(n);
		assertTrue(spy.wellFormed(t));
	}
	
	public void test09() {
		Node n = spy.newNode(new Node[26], false);
		Node n2 = spy.newNode(new Node[26], true);
		Node n3 = spy.newNode(new Node[26], false);
		Node n4 = spy.newNode(new Node[26], true);
		Node n5 = spy.newNode(null, true);
		spy.setNext(n, 2, n3);
		spy.setNext(n3, 4, n4);
		spy.setNext(n4, 24, n2);
		spy.setNext(n4, 4, n5);
		t = spy.create(n);
		assertFalse(spy.wellFormed(t));
	}
	
	public void test010() {
		Node n = spy.newNode(new Node[26], false);
		Node n2 = spy.newNode(null, true);
		Node n3 = spy.newNode(new Node[26], false);
		Node n4 = spy.newNode(new Node[26], false);
		Node n5 = spy.newNode(new Node[26], true);
		spy.setNext(n, 2, n3);
		spy.setNext(n, 25, n2);
		spy.setNext(n3, 4, n4);
		spy.setNext(n4, 4, n5);
		t = spy.create(n);
		assertFalse(spy.wellFormed(t));
	}
	
	public void test011() {
		Node n = spy.newNode(new Node[26], false);
		Node n2 = spy.newNode(new Node[26], true);
		Node n3 = spy.newNode(new Node[26], false);
		Node n4 = spy.newNode(new Node[26], true);
		Node n5 = spy.newNode(new Node[26], true);
		Node n6 = spy.newNode(new Node[26], true);
		Node n7 = spy.newNode(new Node[26], true);
		spy.setNext(n, 2, n3);
		spy.setNext(n3, 4, n4);
		spy.setNext(n3, 14, n6);
		spy.setNext(n3, 20, n7);
		spy.setNext(n4, 24, n2);
		spy.setNext(n4, 4, n5);
		t = spy.create(n);
		assertTrue(spy.wellFormed(t));
	}
	
	public void test012() {
		Node n = spy.newNode(new Node[26], false);
		Node n2 = spy.newNode(new Node[26], true);
		Node n3 = spy.newNode(new Node[26], false);
		Node n4 = spy.newNode(new Node[26], true);
		Node n5 = spy.newNode(new Node[26], true);
		Node n6 = spy.newNode(null, true);
		Node n7 = spy.newNode(null, true);
		spy.setNext(n, 2, n3);
		spy.setNext(n3, 4, n4);
		spy.setNext(n3, 14, n6);
		spy.setNext(n3, 20, n7);
		spy.setNext(n4, 24, n2);
		spy.setNext(n4, 4, n5);
		t = spy.create(n);
		assertFalse(spy.wellFormed(t));
	}
	

	// Children length
	public void test10() {
		Node n = spy.newNode(null, true);
		t = spy.create(n);
		assertFalse(spy.wellFormed(t));
	}

	public void test12() {
		Node n = spy.newNode(new Node[1], true);
		t = spy.create(n);
		assertFalse(spy.wellFormed(t));
	}

	public void test13() {
		Node n = spy.newNode(new Node[27], true);
		t = spy.create(n);
		assertFalse(spy.wellFormed(t));
	}

	public void test14() {
		Node n = spy.newNode(new Node[26], true);
		Node n2 = spy.newNode(new Node[2], false);
		spy.setNext(n, 0, n2);
		t = spy.create(n);
		assertFalse(spy.wellFormed(t));
	}

	public void test15() {
		Node n = spy.newNode(new Node[26], false);
		Node n2 = spy.newNode(null, true);
		spy.setNext(n, 0, n2);
		t = spy.create(n);
		assertFalse(spy.wellFormed(t));
	}
	
	public void test16() {
		Node n = spy.newNode(new Node[26], false);
		t = spy.create(n);
		assertTrue(spy.wellFormed(t));
	}
	
	public void test17() {
		Node n = spy.newNode(new Node[26], false);
		Node n1 = spy.newNode(new Node[26], false);
		Node n2 = spy.newNode(new Node[26], true);
		spy.setNext(n, 0, n1);
		spy.setNext(n1, 10, n2);
		t = spy.create(n);
		assertTrue(spy.wellFormed(t));
	}
	
	public void test18() {
		Node n = spy.newNode(new Node[26], false);
		Node n1 = spy.newNode(new Node[26], true);
		Node n2 = spy.newNode(new Node[26], true);
		spy.setNext(n, 0, n1);
		spy.setNext(n, 10, n2);
		t = spy.create(n);
		assertTrue(spy.wellFormed(t));
	}
	
	public void test19() {
		Node n = spy.newNode(new Node[26], false);
		Node n1 = spy.newNode(new Node[26], true);
		Node n2 = spy.newNode(new Node[26], true);
		Node n3 = spy.newNode(new Node[26], true);
		Node n4 = spy.newNode(new Node[26], true);
		spy.setNext(n, 0, n1);
		spy.setNext(n1, 10, n2);
		spy.setNext(n1, 11, n3);
		spy.setNext(n1, 20, n4);
		t = spy.create(n);
		assertTrue(spy.wellFormed(t));
	}
	
	public void test110() {
		Node n = spy.newNode(new Node[26], false);
		Node n1 = spy.newNode(new Node[26], true);
		Node n2 = spy.newNode(new Node[26], true);
		Node n3 = spy.newNode(new Node[26], true);
		Node n4 = spy.newNode(new Node[26], false);
		Node n5 = spy.newNode(new Node[26], true);
		spy.setNext(n, 0, n1);
		spy.setNext(n1, 10, n2);
		spy.setNext(n1, 11, n3);
		spy.setNext(n1, 20, n4);
		spy.setNext(n4, 24, n5);
		t = spy.create(n);
		assertTrue(spy.wellFormed(t));
	}

	// isEndOfWord
	public void test20() {
		Node n = spy.newNode(new Node[26], true);
		t = spy.create(n);
		assertTrue(spy.wellFormed(t));
	}

	public void test21() {
		Node n = spy.newNode(new Node[26], false);
		t = spy.create(n);
		assertTrue(spy.wellFormed(t));
	}

	public void test22() {
		Node n = spy.newNode(new Node[26], false);
		Node n2 = spy.newNode(new Node[26], false);
		Node n3 = spy.newNode(new Node[26], true);
		spy.setNext(n, 0, n2);
		spy.setNext(n2, 25, n3);
		t = spy.create(n);
		assertTrue(spy.wellFormed(t));
	}

	public void test23() {
		Node n = spy.newNode(new Node[26], false);
		Node n2 = spy.newNode(new Node[26], false);
		Node n3 = spy.newNode(new Node[26], false);
		spy.setNext(n, 0, n2);
		spy.setNext(n2, 0, n3);
		t = spy.create(n);
		assertFalse(spy.wellFormed(t));
	}

	public void test24() {
		Node n = spy.newNode(new Node[26], false);
		Node n2 = spy.newNode(new Node[26], false);
		spy.setNext(n, 0, n2);
		t = spy.create(n);
		assertFalse(spy.wellFormed(t));
	}

	public void test25() {
		Node n = spy.newNode(new Node[26], false);
		Node n2 = spy.newNode(new Node[26], true);
		spy.setNext(n, 0, n2);
		t = spy.create(n);
		assertTrue(spy.wellFormed(t));
	}
	
	public void test26() {
		Node n = spy.newNode(new Node[26], false);
		Node n2 = spy.newNode(new Node[26], true);
		Node n3 = spy.newNode(new Node[26], false);
		spy.setNext(n, 0, n2);
		spy.setNext(n2, 0, n3);
		t = spy.create(n);
		assertFalse(spy.wellFormed(t));
	}
	
	public void test27() {
		Node n = spy.newNode(new Node[26], false);
		Node n2 = spy.newNode(new Node[26], false);
		Node n3 = spy.newNode(new Node[26], true);
		Node n4 = spy.newNode(new Node[26], true);
		spy.setNext(n, 2, n3);
		spy.setNext(n, 4, n4);
		spy.setNext(n, 24, n2);
		t = spy.create(n);
		assertFalse(spy.wellFormed(t));
	}
	
	public void test28() {
		Node n = spy.newNode(new Node[26], false);
		Node n2 = spy.newNode(new Node[26], true);
		Node n3 = spy.newNode(new Node[26], true);
		spy.setNext(n, 2, n3);
		spy.setNext(n, 24, n2);
		t = spy.create(n);
		assertTrue(spy.wellFormed(t));
	}
	
	public void test29() {
		Node n = spy.newNode(new Node[26], false);
		Node n2 = spy.newNode(new Node[26], false);
		Node n3 = spy.newNode(new Node[26], true);
		spy.setNext(n, 2, n3);
		spy.setNext(n, 24, n2);
		t = spy.create(n);
		assertFalse(spy.wellFormed(t));
	}
	
	public void test210() {
		Node n = spy.newNode(new Node[26], false);
		Node n2 = spy.newNode(new Node[26], false);
		Node n3 = spy.newNode(new Node[26], true);
		spy.setNext(n, 2, n2);
		spy.setNext(n2, 24, n3);
		t = spy.create(n);
		assertTrue(spy.wellFormed(t));
	}
	
	public void test211() {
		Node n = spy.newNode(new Node[26], false);
		Node n2 = spy.newNode(new Node[26], true);
		Node n3 = spy.newNode(new Node[26], true);
		spy.setNext(n, 2, n2);
		spy.setNext(n, 24, n3);
		t = spy.create(n);
		assertTrue(spy.wellFormed(t));
	}
	
	public void test212() {
		Node n = spy.newNode(new Node[26], false);
		Node n2 = spy.newNode(new Node[26], true);
		Node n3 = spy.newNode(new Node[26], true);
		Node n4 = spy.newNode(new Node[26], true);
		spy.setNext(n, 2, n2);
		spy.setNext(n, 24, n3);
		spy.setNext(n, 21, n4);
		t = spy.create(n);
		assertTrue(spy.wellFormed(t));
	}
	
	public void test213() {
		Node n = spy.newNode(new Node[26], false);
		Node n2 = spy.newNode(new Node[26], false);
		Node n3 = spy.newNode(new Node[26], true);
		Node n4 = spy.newNode(new Node[26], true);
		spy.setNext(n, 2, n2);
		spy.setNext(n2, 24, n3);
		spy.setNext(n2, 21, n4);
		t = spy.create(n);
		assertTrue(spy.wellFormed(t));
	}
	
	public void test214() {
		Node n = spy.newNode(new Node[26], false);
		Node n2 = spy.newNode(new Node[26], true);
		Node n3 = spy.newNode(new Node[26], true);
		Node n4 = spy.newNode(new Node[26], true);
		spy.setNext(n, 2, n2);
		spy.setNext(n2, 24, n3);
		spy.setNext(n2, 21, n4);
		t = spy.create(n);
		assertTrue(spy.wellFormed(t));
	}
	
	public void test215() {
		Node n = spy.newNode(new Node[26], false);
		Node n2 = spy.newNode(new Node[26], true);
		Node n3 = spy.newNode(new Node[26], true);
		Node n4 = spy.newNode(new Node[26], false);
		Node n5 = spy.newNode(new Node[26], true);
		spy.setNext(n, 2, n2);
		spy.setNext(n2, 24, n3);
		spy.setNext(n2, 21, n4);
		spy.setNext(n4, 1, n5);
		t = spy.create(n);
		assertTrue(spy.wellFormed(t));
	}
	
	public void test216() {
		Node n = spy.newNode(new Node[26], false);
		Node n2 = spy.newNode(new Node[26], true);
		Node n3 = spy.newNode(new Node[26], true);
		Node n4 = spy.newNode(new Node[26], true);
		Node n5 = spy.newNode(new Node[26], true);
		spy.setNext(n, 2, n2);
		spy.setNext(n2, 24, n3);
		spy.setNext(n2, 21, n4);
		spy.setNext(n4, 1, n5);
		t = spy.create(n);
		assertTrue(spy.wellFormed(t));
	}


	// cycle
	public void test30() {
		Node n = spy.newNode(new Node[26], true);
		spy.setNext(n, 0, n);
		t = spy.create(n);
		assertFalse(spy.wellFormed(t));
	}

	public void test31() {
		Node n = spy.newNode(new Node[26], false);
		Node n2 = spy.newNode(new Node[26], true);
		spy.setNext(n, 0, n2);
		spy.setNext(n2, 0, n2);
		t = spy.create(n);
		assertFalse(spy.wellFormed(t));
	}

	public void test32() {
		Node n = spy.newNode(new Node[26], false);
		Node n2 = spy.newNode(new Node[26], false);
		spy.setNext(n, 10, n2);
		spy.setNext(n2, 25, n2);
		t = spy.create(n);
		assertFalse(spy.wellFormed(t));
	}

	public void test33() {
		Node n = spy.newNode(new Node[26], true);
		Node n2 = spy.newNode(new Node[26], false);
		spy.setNext(n, 0, n2);
		spy.setNext(n2, 0, n);
		t = spy.create(n);
		assertFalse(spy.wellFormed(t));
	}

	public void test34() {
		Node n = spy.newNode(new Node[26], false);
		Node n2 = spy.newNode(new Node[26], false);
		spy.setNext(n, 25, n2);
		spy.setNext(n2, 0, n);
		t = spy.create(n);
		assertFalse(spy.wellFormed(t));
	}

	public void test35() {
		Node n = spy.newNode(new Node[26], false);
		Node n2 = spy.newNode(new Node[26], false);
		Node n3 = spy.newNode(new Node[26], false);
		spy.setNext(n, 25, n2);
		spy.setNext(n2, 0, n3);
		spy.setNext(n2, 15, n);
		t = spy.create(n);
		assertFalse(spy.wellFormed(t));
	}
	
	public void test36() {
		Node n = spy.newNode(new Node[26], false);
		spy.setNext(n, 0, n);
		t = spy.create(n);
		assertFalse(spy.wellFormed(t));
	}
	
	public void test37() {
		Node n = spy.newNode(new Node[26], false);
		Node n2 = spy.newNode(new Node[26], false);
		Node n3 = spy.newNode(new Node[26], true);
		spy.setNext(n, 25, n2);
		spy.setNext(n2, 0, n3);
		spy.setNext(n2, 15, n2);
		t = spy.create(n);
		assertFalse(spy.wellFormed(t));
	}
	
	public void test38() {
		Node n = spy.newNode(new Node[26], false);
		Node n2 = spy.newNode(new Node[26], false);
		Node n3 = spy.newNode(new Node[26], true);
		spy.setNext(n, 25, n2);
		spy.setNext(n2, 0, n3);
		spy.setNext(n2, 15, n2);
		t = spy.create(n);
		assertFalse(spy.wellFormed(t));
	}
	
	public void test39() {
		Node n = spy.newNode(new Node[26], false);
		Node n2 = spy.newNode(new Node[26], false);
		Node n3 = spy.newNode(new Node[26], true);
		Node n4 = spy.newNode(new Node[26], true);
		Node n5 = spy.newNode(new Node[26], true);
		spy.setNext(n, 25, n2);
		spy.setNext(n, 5, n4);
		spy.setNext(n, 15, n5);
		spy.setNext(n2, 0, n3);
		spy.setNext(n2, 15, n2);
		t = spy.create(n);
		assertFalse(spy.wellFormed(t));
	}
	
	public void test310() {
		Node n = spy.newNode(new Node[26], false);
		Node n2 = spy.newNode(new Node[26], false);
		Node n3 = spy.newNode(new Node[26], true);
		Node n4 = spy.newNode(new Node[26], true);
		Node n5 = spy.newNode(new Node[26], true);
		spy.setNext(n, 25, n2);
		spy.setNext(n, 5, n4);
		spy.setNext(n, 15, n5);
		spy.setNext(n2, 0, n3);
		spy.setNext(n2, 15, n);
		t = spy.create(n);
		assertFalse(spy.wellFormed(t));
	}
	
	
	// different nodes point to same child but does not forms cycle. 
	
	public void test40() {
		Node n = spy.newNode(new Node[26], false);
		Node n2 = spy.newNode(new Node[26], true);
		spy.setNext(n, 25, n2);
		spy.setNext(n, 0, n2);
		t = spy.create(n);
		assertFalse(spy.wellFormed(t));
	}
	
	public void test41() {
		Node n = spy.newNode(new Node[26], false);
		Node n2 = spy.newNode(new Node[26], false);
		Node n3 = spy.newNode(new Node[26], true);
		spy.setNext(n, 25, n2);
		spy.setNext(n2, 0, n3);
		spy.setNext(n2, 15, n3);
		t = spy.create(n);
		assertFalse(spy.wellFormed(t));
	}
	
	public void test42() {
		Node n = spy.newNode(new Node[26], false);
		Node n2 = spy.newNode(new Node[26], true);
		Node n3 = spy.newNode(new Node[26], false);
		Node n4 = spy.newNode(new Node[26], true);
		spy.setNext(n, 0, n4);
		spy.setNext(n, 10, n2);
		spy.setNext(n, 20, n3);
		spy.setNext(n3, 24, n2);
		spy.setNext(n3, 20, n4);
		t = spy.create(n);
		assertFalse(spy.wellFormed(t));
	}
	
	public void test43() {
		Node n = spy.newNode(new Node[26], false);
		Node n2 = spy.newNode(new Node[26], false);
		Node n3 = spy.newNode(new Node[26], true);
		spy.setNext(n, 25, n2);
		spy.setNext(n2, 0, n3);
		spy.setNext(n, 15, n2);
		t = spy.create(n);
		assertFalse(spy.wellFormed(t));
	}
	
	public void test44() {
		Node n = spy.newNode(new Node[26], false);
		Node n2 = spy.newNode(new Node[26], false);
		Node n3 = spy.newNode(new Node[26], true);
		spy.setNext(n, 25, n2);
		spy.setNext(n2, 0, n3);
		spy.setNext(n2, 15, n3);
		spy.setNext(n3, 10, null);
		t = spy.create(n);
		assertFalse(spy.wellFormed(t));
	}
	
	public void test45() {
		Node n = spy.newNode(new Node[26], false);
		Node n2 = spy.newNode(new Node[26], false);
		Node n3 = spy.newNode(new Node[26], true);
		spy.setNext(n, 25, n2);
		spy.setNext(n2, 25, n3);
		spy.setNext(n2, 15, n3);
		t = spy.create(n);
		assertFalse(spy.wellFormed(t));
	}
	
	public void test46() {
		Node n = spy.newNode(new Node[26], false);
		Node n2 = spy.newNode(new Node[26], false);
		Node n3 = spy.newNode(new Node[26], true);
		Node n4 = spy.newNode(new Node[26], true);
		spy.setNext(n3, 1, n4);
		spy.setNext(n, 25, n2);
		spy.setNext(n2, 25, n3);
		spy.setNext(n2, 15, n3);
		t = spy.create(n);
		assertFalse(spy.wellFormed(t));
	}
	
	public void test47() {
		Node n = spy.newNode(new Node[26], false);
		Node n2 = spy.newNode(new Node[26], false);
		Node n3 = spy.newNode(new Node[26], true);
		Node n4 = spy.newNode(new Node[26], true);
		spy.setNext(n3, 1, n4);
		spy.setNext(n3, 10, n4);
		spy.setNext(n, 25, n2);
		spy.setNext(n2, 25, n3);
		spy.setNext(n2, 15, n3);
		t = spy.create(n);
		assertFalse(spy.wellFormed(t));
	}
	
	public void test48() {
		Node n = spy.newNode(new Node[26], false);
		Node n1 = spy.newNode(new Node[26], true);
		Node n2 = spy.newNode(new Node[26], false);
		Node n3 = spy.newNode(new Node[26], true);
		Node n4 = spy.newNode(new Node[26], true);
		spy.setNext(n3, 1, n4);
		spy.setNext(n3, 10, n4);
		spy.setNext(n, 25, n2);
		spy.setNext(n, 2, n1);
		spy.setNext(n2, 25, n3);
		spy.setNext(n2, 15, n3);
		t = spy.create(n);
		assertFalse(spy.wellFormed(t));
	}
	
	public void test49() {
		Node n = spy.newNode(new Node[26], false);
		Node n1 = spy.newNode(new Node[26], false);
		Node n2 = spy.newNode(new Node[26], false);
		Node n3 = spy.newNode(new Node[26], true);
		Node n4 = spy.newNode(new Node[26], true);
		Node n5 = spy.newNode(new Node[26], false);
		Node n6 = spy.newNode(new Node[26], true);
		spy.setNext(n3, 1, n4);
		spy.setNext(n3, 10, n4);
		spy.setNext(n, 25, n2);
		spy.setNext(n, 2, n1);
		spy.setNext(n2, 25, n3);
		spy.setNext(n2, 15, n3);
		spy.setNext(n1, 0, n5);
		spy.setNext(n1, 15, n6);
		t = spy.create(n);
		assertFalse(spy.wellFormed(t));
	}
	
	
	
	

}