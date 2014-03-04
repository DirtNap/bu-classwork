package edu.bu.cs342.p01;

import static org.junit.Assert.*;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class ContactListTest {

    private String testName;
    private String testEmail;
    private String testPhone;
    private ContactEntry testContactEntry;
    private ContactList testContactList;

    @Before
    public void setUp() throws Exception {
        this.testName = "Test Name";
        this.testEmail = "test@example.com";
        this.testPhone = "(555) 555-1212";
        this.testContactEntry = new ContactEntry(this.testName, this.testEmail, this.testPhone);
        this.testContactList = new ContactList();
    }

    @Test
    public void testClear() {
        assertEquals(0, this.testContactList.length());
        this.testContactList.addContact(this.testContactEntry);
        assertEquals(1, this.testContactList.length());
        this.testContactList.clear();
        assertFalse(this.testContactList.iterator().hasNext());
        assertEquals(0, this.testContactList.length());
    }

    @Test
    public void testLength() throws ContactValidationException {
        assertEquals(0, this.testContactList.length());
        this.testContactList.addContact(this.testContactEntry);
        assertEquals(1, this.testContactList.length());
        this.testContactList.addContact(new ContactEntry("aaa", "aaa@example.com", "1111111"));
        assertEquals(2, this.testContactList.length());
    }

    @Test
    public void testAddContact() {
        assertEquals(0, this.testContactList.length());
        this.testContactList.addContact(this.testContactEntry);
        assertEquals(this.testContactEntry, this.testContactList.iterator().next());
    }

    @Test
    public void testAddContactDuplicate() {
        assertEquals(0, this.testContactList.length());
        this.testContactList.addContact(this.testContactEntry);
        assertEquals(1, this.testContactList.length());
        this.testContactList.addContact(this.testContactEntry);
        assertEquals(1, this.testContactList.length());
    }

    @Test
    public void testRemoveContact() throws ContactValidationException {
        ContactEntry localTestContactEntry = new ContactEntry("zzzz", "zzz@example.com", "1111111");
        assertEquals(0, this.testContactList.length());
        this.testContactList.addContact(this.testContactEntry);
        assertEquals(1, this.testContactList.length());
        this.testContactList.addContact(localTestContactEntry);
        assertEquals(2, this.testContactList.length());
        assertEquals(this.testContactEntry, this.testContactList.iterator().next());
        this.testContactList.removeContact(this.testContactEntry);
        assertEquals(1, this.testContactList.length());
        assertEquals(localTestContactEntry, this.testContactList.iterator().next());
    }

    @Test
    public void testSearchByContactName() throws ContactValidationException {
        List<ContactEntry> localTestResults;
        ContactEntry localTestContactEntry = new ContactEntry("aaa", "aaa@example.com", "1111111");
        this.testContactList.addContact(this.testContactEntry);
        this.testContactList.addContact(localTestContactEntry);
        localTestResults = this.testContactList.searchByContactEmail(this.testEmail.substring(0,
                this.testEmail.length() - 1).substring(1));
        assertEquals(1, localTestResults.size());
        assertEquals(this.testContactEntry, localTestResults.get(0));
        localTestResults = this.testContactList.searchByContactEmail("aa");
        assertEquals(1, localTestResults.size());
        assertEquals(localTestContactEntry, localTestResults.get(0));
        localTestResults = this.testContactList.searchByContactEmail("example.com");
        assertEquals(2, localTestResults.size());
        assertTrue(localTestResults.contains(this.testContactEntry));
        assertTrue(localTestResults.contains(localTestContactEntry));
    }

    @Test
    public void testSearchByContactEmail() throws ContactValidationException {
        List<ContactEntry> localTestResults;
        ContactEntry localTestContactEntry = new ContactEntry("aaa", "aaa@example.com", "1111111");
        this.testContactList.addContact(this.testContactEntry);
        this.testContactList.addContact(localTestContactEntry);
        System.out.println(this.testName.substring(0, this.testName.length() - 1).substring(1));
        localTestResults = this.testContactList.searchByContactName(this.testName.substring(0,
                this.testName.length() - 1).substring(1));
        assertEquals(1, localTestResults.size());
        assertEquals(this.testContactEntry, localTestResults.get(0));
        localTestResults = this.testContactList.searchByContactName("aa");
        assertEquals(1, localTestResults.size());
        assertEquals(localTestContactEntry, localTestResults.get(0));
        localTestResults = this.testContactList.searchByContactName("a");
        assertEquals(2, localTestResults.size());
        assertTrue(localTestResults.contains(this.testContactEntry));
        assertTrue(localTestResults.contains(localTestContactEntry));
    }

    @Test
    public void testEquals() {
        this.testContactList.addContact(this.testContactEntry);
        assertFalse(this.testContactList.equals(null));
        assertTrue(this.testContactList.equals(this.testContactList));
        ContactList localTestContactList = new ContactList();
        assertFalse(this.testContactList.equals(localTestContactList));
        localTestContactList.addContact(this.testContactEntry);
        assertTrue(this.testContactList.equals(localTestContactList));
    }
}
