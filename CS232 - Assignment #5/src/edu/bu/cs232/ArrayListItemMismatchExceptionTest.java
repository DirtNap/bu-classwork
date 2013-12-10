package edu.bu.cs232;

import static org.junit.Assert.*;

import org.junit.Test;

public class ArrayListItemMismatchExceptionTest {

	@Test
	public final void testArrayListItemMismatchExceptionMessages() {
		String testString = "Test";
		try {
			throw new ArrayListItemMismatchException(testString);
		} catch (ArrayListItemMismatchException ex) {
			assertEquals(testString, ex.getMessage());
		}
		try {
			throw new ArrayListItemMismatchException();
		} catch (ArrayListItemMismatchException ex) {
			assertEquals(ArrayListItemMismatchException.DEFAULT_MESSAGE, ex.getMessage());
		}
	}

	@Test(expected=ArrayListItemMismatchException.class)
	public final void testArrayListItemMismatchException() {
		throw new ArrayListItemMismatchException();
	}

}
