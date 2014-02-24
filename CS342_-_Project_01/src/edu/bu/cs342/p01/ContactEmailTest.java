package edu.bu.cs342.p01;

import static org.junit.Assert.*;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

public class ContactEmailTest {

    private String testLongEmail;
    private String testShortEmail;
    private String simpleUserName;
    private String simpleDomain;
    private String complexUserName;
    private String complexDomain;

    @Before
    public void setUp() throws Exception {
        this.simpleUserName = "aaa";
        this.simpleDomain = "aaa.aaa";
        this.complexUserName = "aaa.aaa";
        this.complexDomain = "aaa.aaa.aaa.aaa";
        this.testShortEmail = String.format("%s@%s", this.simpleUserName, this.simpleDomain);
        this.testLongEmail = String.format("%s@%s", this.complexUserName, this.complexDomain);
    }

    @Test
    public void testContactEmail() throws ContactValidationException {
        ContactEmail testContactEmail = new ContactEmail(this.testShortEmail);
        assertEquals("Correct Username", this.simpleUserName, testContactEmail.userName);
        assertEquals("Correct Domain", this.simpleDomain, testContactEmail.domain);
        testContactEmail = new ContactEmail(this.testLongEmail);
        assertEquals("Correct Username", this.complexUserName, testContactEmail.userName);
        assertEquals("Correct Domain", this.complexDomain, testContactEmail.domain);
        testContactEmail = new ContactEmail(this.testLongEmail.toUpperCase());
        assertEquals("Correct Username Case", this.complexUserName, testContactEmail.userName);
        assertEquals("Correct Domain Case", this.complexDomain, testContactEmail.domain);
    }

    @Test(expected = ContactValidationException.class)
    public void testContactEmailValidationDomain() throws ContactValidationException {
        new ContactEmail("username@domain");
    }

    @Test(expected = ContactValidationException.class)
    public void testContactEmailValidationDomainChars() throws ContactValidationException {
        new ContactEmail("username@domain!.com");
    }

    @Test(expected = ContactValidationException.class)
    public void testContactEmailValidationformat() throws ContactValidationException {
        new ContactEmail("username@domain.com@domain.net");
    }

    @Test
    public void testEqualsObject() throws ContactValidationException {
        ContactEmail testContactEmail1 = new ContactEmail(this.testShortEmail);
        ContactEmail testContactEmail2 = new ContactEmail(this.testLongEmail);
        assertFalse("Doesn't equal null", testContactEmail1.equals(null));
        assertTrue("Equals itself", testContactEmail1.equals(testContactEmail1));
        assertTrue("Equals itself", testContactEmail2.equals(testContactEmail2));
        assertFalse("Check different addresses", testContactEmail1.equals(testContactEmail2));
        testContactEmail2 = new ContactEmail(this.testShortEmail);
        assertTrue("Check value equality", testContactEmail1.equals(testContactEmail2));
        assertTrue("Check symmetry", testContactEmail2.equals(testContactEmail1));
    }

    @Test
    public void testCompareTo() throws ContactValidationException {
        ContactEmail[] sortedTestEmail = new ContactEmail[] { new ContactEmail("bob@google.com"),
                new ContactEmail("abc@bu.edu"), new ContactEmail("xyz@bu.edu"),
                new ContactEmail("bob@harvard.edu"), new ContactEmail("bob@extension.harvard.edu"),
                new ContactEmail("bob@fas.harvard.edu"), new ContactEmail("staff@thirteen.net"),
                new ContactEmail("aaa@aaa.org") };
        ContactEmail[] unSortedTestEmail = new ContactEmail[] {
                new ContactEmail("bob@extension.harvard.edu"), new ContactEmail("aaa@aaa.org"),
                new ContactEmail("xyz@bu.edu"), new ContactEmail("abc@bu.edu"),
                new ContactEmail("bob@harvard.edu"), new ContactEmail("bob@fas.harvard.edu"),
                new ContactEmail("bob@google.com"), new ContactEmail("staff@thirteen.net") };
        Arrays.sort(unSortedTestEmail);
        assertArrayEquals("Array Sorting", sortedTestEmail, unSortedTestEmail);
    }

    @Test
    public void testToString() throws ContactValidationException {
        ContactEmail testContactEmail = new ContactEmail(this.testShortEmail);
        assertEquals("Simple email string", this.testShortEmail, testContactEmail.toString());
        testContactEmail = new ContactEmail(this.testLongEmail);
        assertEquals("Complex email string", this.testLongEmail, testContactEmail.toString());
    }
}
