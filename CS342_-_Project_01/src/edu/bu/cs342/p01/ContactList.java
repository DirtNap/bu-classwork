package edu.bu.cs342.p01;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.bu.cs342.utilities.SortedLinkedList;

public class ContactList implements Iterable<ContactEntry> {

    private SortedLinkedList<ContactEntry> contactEntryList;

    public ContactList() {
        this.contactEntryList = new SortedLinkedList<ContactEntry>();
    }

    public int length() {
        return this.contactEntryList.length();
    }

    public void addContact(String name, String email, String phone)
            throws ContactValidationException {
        this.addContact(new ContactEntry(name, email, phone));
    }

    public void addContact(ContactEntry contact) {
        this.contactEntryList.addItem(contact);
    }

    public int write(String file) throws FileNotFoundException, IOException {
        try (FileOutputStream stream = new FileOutputStream(file)) {
            return this.write(stream);
        }
    }

    public int write(OutputStream file) throws IOException {
        int ct = 0;
        try (ObjectOutputStream output = new ObjectOutputStream(file)) {
            output.writeInt(this.length());
            for (ContactEntry e : this) {
                output.writeObject(e);
                ++ct;
            }
            output.writeInt(ct);
        }
        return ct;
    }

    public int reload(String file) throws IOException {
        try (FileInputStream stream = new FileInputStream(file)) {
            return this.reload(stream);
        }
    }

    public int reload(InputStream file) throws IOException {
        SortedLinkedList<ContactEntry> replacement = new SortedLinkedList<>();
        try (ObjectInputStream input = new ObjectInputStream(file)) {
            int finalCount, targetCount;
            targetCount = input.readInt();
            for (int i = 0; i < targetCount; ++i) {
                replacement.addItem((ContactEntry) input.readObject());
            }
            finalCount = input.readInt();
            if (finalCount == targetCount && finalCount == replacement.length()) {
                this.contactEntryList = replacement;
            } else {
                throw new IOException("Invalid serialization format.");
            }
        } catch (ClassNotFoundException | ClassCastException ex) {
            return -1;
        }
        return this.contactEntryList.length();
    }

    @Override
    public boolean equals(Object o) {
        if (null == o) {
            return false;
        }
        if (this == o) {
            return true;
        }
        try {
            ContactList test = (ContactList) o;
            if (this.length() == test.length()) {
                Iterator<ContactEntry> iTest = test.iterator();
                for (ContactEntry local : this) {
                    if (!iTest.hasNext()) {
                        return false;
                    }
                    if (!local.equals(iTest.next())) {
                        return false;
                    }
                }
                return true;
            }
            return false;
        } catch (ClassCastException | NoSuchElementException ex) {
            return false;
        }
    }

    @Override
    public Iterator<ContactEntry> iterator() {
        return this.contactEntryList.iterator();
    }
}
