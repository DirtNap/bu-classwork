package edu.bu.cs342.p01;

import static org.junit.Assert.*;
import org.junit.Test;

public class ContactValidationExceptionTest {

    @Test(expected = ContactValidationException.class)
    public void testThowable() throws ContactValidationException {
        throw new ContactValidationException();
    }

    @Test
    public void testContactValidationExceptionMessage() {
        String testValue = "Test";
        String testCase = "";
        try {
            throw new ContactValidationException(testValue);
        } catch (ContactValidationException ex) {
            testCase = ex.getMessage();
        }
        assertEquals(testValue, testCase);
    }

    @Test
    public void testContactValidationExceptionFormattedMessage() {
        String testValue = "Test";
        String testCase = "";
        try {
            throw new ContactValidationException(testValue, testValue);
        } catch (ContactValidationException ex) {
            testCase = ex.getMessage();
        }
        assertEquals(String.format(ContactValidationException.VALIDATION_EXCEPTION_FORMAT,
                testValue, testValue), testCase);
    }
}
