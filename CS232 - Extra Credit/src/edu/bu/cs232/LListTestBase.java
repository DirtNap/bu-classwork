package edu.bu.cs232;

import org.junit.Before;


public class LListTestBase {

	protected LList<String> testLList;
	protected LList<String> localTestList;
	protected String[] testArray;

	@Before
	public void setUp() throws Exception {
		this.testLList = new LList<String>();
		this.localTestList = new LList<String>();
		this.testArray = new String[] { "first", "second", "third", "fourth", "fifth" };
		for (String word : this.testArray) {
			this.testLList.add(word);
		}
	}
}
