package edu.bu.cs342.p01;

import static org.junit.Assert.*;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

public class ContactEntryTest {

    private ContactEntry testContactEntry;
    private String testInputName;
    private String testOutputName;
    private String testEmail;
    private String testPhone;

    @Before
    public void setUp() throws Exception {
        this.testInputName = "Name Test";
        this.testOutputName = "Test, Name";
        this.testEmail = "address@example.com";
        this.testPhone = "(111) 111-1111";
        this.testContactEntry = new ContactEntry(this.testInputName, this.testEmail, this.testPhone);
    }

    @Test
    public void testHashCode() {
        assertEquals(this.testContactEntry.toString().hashCode(), this.testContactEntry.hashCode());
    }

    @Test
    public void testParseName() throws ContactValidationException {
        ContactEntry singleName = new ContactEntry("name", this.testContactEntry.getEmail()
                .toString(), this.testContactEntry.getPhone().toString());
        ContactEntry complexName = new ContactEntry("first compound-last", this.testContactEntry
                .getEmail().toString(), this.testContactEntry.getPhone().toString());
        ContactEntry multiPartName = new ContactEntry("Name with multiple parts",
                this.testContactEntry.getEmail().toString(), this.testContactEntry.getPhone()
                        .toString());
        assertEquals("name", singleName.getName());
        assertEquals("parts, Name with multiple", multiPartName.getName());
        assertEquals("compound-last, first", complexName.getName());
    }

    @Test
    public void testGetName() {
        assertEquals(this.testOutputName, this.testContactEntry.getName());
    }

    @Test
    public void testGetEmail() {
        assertEquals(this.testEmail, this.testContactEntry.getEmail().toString());
    }

    @Test
    public void testGetPhone() {
        assertEquals(this.testPhone, this.testContactEntry.getPhone().toString());
    }

    @Test
    public void testEqualsObject() throws ContactValidationException {
        assertFalse(this.testContactEntry.equals(null));
        assertTrue(this.testContactEntry.equals(this.testContactEntry));
        assertFalse(this.testContactEntry.equals(new Object()));
        ContactEntry localTestContactEntry;
        localTestContactEntry = new ContactEntry(this.testInputName, this.testEmail, this.testPhone);
        assertTrue(this.testContactEntry.equals(localTestContactEntry));
        localTestContactEntry = new ContactEntry(String.format("X%s", this.testInputName),
                this.testEmail, this.testPhone);
        assertFalse(this.testContactEntry.equals(localTestContactEntry));
        localTestContactEntry = new ContactEntry(this.testInputName, String.format("X%s",
                this.testEmail), this.testPhone);
        assertFalse(this.testContactEntry.equals(localTestContactEntry));
        localTestContactEntry = new ContactEntry(this.testInputName, this.testEmail,
                this.testPhone.replace('1', '2'));
        assertFalse(this.testContactEntry.equals(localTestContactEntry));
    }

    @Test
    public void testCompareTo() throws ContactValidationException {
        ContactEntry[] sortedContactEntryTest = new ContactEntry[] {
                new ContactEntry("A A", "zzz@zzz.zzz", "(111) 111-1111"),
                new ContactEntry("A B", "azz@zzz.zzz", "(111) 111-1111"),
                new ContactEntry("A B", "zzz@zzz.zzz", "(111) 111-1110"),
                new ContactEntry("A B", "zzz@zzz.zzz", "(111) 111-1111"),
                new ContactEntry("A C", "aaa@aaa.aaa", "(111) 111-1111"),
                new ContactEntry("A D", "zzz@zzz.zzz", "(000) 000-0000") };
        ContactEntry[] unSortedContactEntryTest = new ContactEntry[] {
                new ContactEntry("A B", "azz@zzz.zzz", "(111) 111-1111"),
                new ContactEntry("A A", "zzz@zzz.zzz", "(111) 111-1111"),
                new ContactEntry("A B", "zzz@zzz.zzz", "(111) 111-1111"),
                new ContactEntry("A B", "zzz@zzz.zzz", "(111) 111-1110"),
                new ContactEntry("A D", "zzz@zzz.zzz", "(000) 000-0000"),
                new ContactEntry("A C", "aaa@aaa.aaa", "(111) 111-1111") };
        for (int i = 0; i < sortedContactEntryTest.length; ++i) {
            assertFalse(sortedContactEntryTest[i].equals(unSortedContactEntryTest[i]));
        }
        Arrays.sort(unSortedContactEntryTest);
        for (int i = 0; i < sortedContactEntryTest.length; ++i) {
            assertTrue(sortedContactEntryTest[i].equals(unSortedContactEntryTest[i]));
        }
    }

}
