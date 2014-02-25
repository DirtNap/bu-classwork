package edu.bu.cs342.p01;

import static org.junit.Assert.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class ContactListSerializationTest {
    private ContactEntry[] testContactEntries;
    private ContactList testContactList;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void setUp() throws Exception {
        this.testContactEntries = new ContactEntry[] {
                new ContactEntry("abc", "abc@bu.edu", "1234567890"),
                new ContactEntry("xyz", "xyz@bu.edu", "8905671234") };
        Arrays.sort(this.testContactEntries);
        this.testContactList = new ContactList();
        for (int i = 0; i < this.testContactEntries.length; ++i) {
            this.testContactList.addContact(this.testContactEntries[i]);
        }
    }

    @Test
    public void testWriteOutputStream() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        this.testContactList.write(bos);
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        assertEquals("Checking initial count", this.testContactEntries.length, ois.readInt());
        for (int i = 0; i < this.testContactEntries.length; ++i) {
            assertEquals("Checking retrieved objects", this.testContactEntries[i],
                    (ContactEntry) ois.readObject());
        }
        assertEquals("Checking final count", this.testContactEntries.length, ois.readInt());
    }

    @Test
    public void testReloadInputStream() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeInt(this.testContactEntries.length);
        for (int i = 0; i < this.testContactEntries.length; ++i) {
            oos.writeObject(this.testContactEntries[i]);
        }
        oos.writeInt(this.testContactEntries.length);
        oos.close();
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ContactList localTestContactList = new ContactList();
        localTestContactList.reload(bis);
        assertEquals("Check reserialized contact list", this.testContactList, localTestContactList);
    }

    @Test
    public void testFileOperations() throws IOException {
        File serializeFile = this.folder.newFile();
        assertEquals("File write test", this.testContactEntries.length,
                this.testContactList.write(serializeFile.getAbsolutePath()));
        assertEquals("File write test", this.testContactEntries.length,
                this.testContactList.reload(serializeFile.getAbsolutePath()));
    }
}
