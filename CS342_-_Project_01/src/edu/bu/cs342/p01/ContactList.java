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
import java.util.List;
import java.util.NoSuchElementException;
import edu.bu.cs342.utilities.Searchable.SearchScope;
import edu.bu.cs342.utilities.SearchableSortedLinkedList;

/**
 * A list of {@link ContactEntry} elements representing an address book
 * 
 * @author Michael Donnelly
 * 
 */
public class ContactList implements Iterable<ContactEntry> {

    private SearchableSortedLinkedList<ContactEntry> contactEntryList;

    public ContactList() {
        this.clear();
    }

    /**
     * Empties out the contact list.
     */
    public void clear() {
        this.contactEntryList = new SearchableSortedLinkedList<ContactEntry>(false);
    }

    /**
     * The length of the contact list.
     * 
     * @return int the length of the contact list.
     */
    public int length() {
        return this.contactEntryList.length();
    }

    public void addContact(String name, String email, String phone)
            throws ContactValidationException {
        this.addContact(new ContactEntry(name, email, phone));
    }

    /**
     * Adds a new contact to the list, in ascending order.
     * 
     * @param contact
     *            ContactEntry the contact to add.
     */
    public void addContact(ContactEntry contact) {
        this.contactEntryList.addItem(contact);
    }

    public boolean removeContact(String name, String email, String phone)
            throws ContactValidationException {
        return this.removeContact(new ContactEntry(name, email, phone));
    }

    /**
     * Removes a contact from the list.
     * 
     * @param contact
     *            ContactEntry the contact to remove.
     * @return boolean true if the contact was found in the list and removed.
     */
    public boolean removeContact(ContactEntry contact) {
        return this.contactEntryList.removeItem(contact);
    }

    /**
     * Returns a list of contacts whose name partially matches the provided
     * text.
     * 
     * @param name
     *            String the text to match.
     * @return List<ContactEntry> the matching elements of the contact list.
     */
    public List<ContactEntry> searchByContactName(String name) {
        return this.contactEntryList.search("name", name, SearchScope.PARTIAL);
    }

    /**
     * Returns a list of contacts whose email partially matches the provided
     * text.
     * 
     * @param email
     *            String the text to match.
     * @return List<ContactEntry> the matching elements of the contact list.
     */
    public List<ContactEntry> searchByContactEmail(String email) {
        return this.contactEntryList.search("email", email, SearchScope.PARTIAL);
    }

    /**
     * Store the contact list into a file.
     * 
     * @param file
     *            String the name of the file.
     * @return int the number of contacts written to the file.
     * @throws FileNotFoundException
     *             when the file name is not appropriate.
     * @throws IOException
     *             when the underlying file stream can not be written to.
     */
    public int write(String file) throws FileNotFoundException, IOException {
        try (FileOutputStream stream = new FileOutputStream(file)) {
            return this.write(stream);
        }
    }

    /**
     * Store the contact list into a file.
     * 
     * @param file
     *            OutputStream the stream to write the file to.
     * @return int the number of contacts written to the file.
     * @throws IOException
     *             When the stream can not be written to.
     */
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

    /**
     * Read the contact list from a file.
     * 
     * Replaces the existing list of contacts.
     * 
     * @param file
     *            String the name of the file.
     * @return int the number of contacts read from the file, or -1 when the
     *         file could not be loaded.
     * @throws FileNotFoundException
     *             when the file name is not appropriate.
     * @throws IOException
     *             when the underlying file stream is corrupt.
     */

    public int reload(String file) throws IOException {
        try (FileInputStream stream = new FileInputStream(file)) {
            return this.reload(stream);
        }
    }

    /**
     * Read the contact list from a file.
     * 
     * Replaces the existing list of contacts.
     * 
     * @param file
     *            The stream to read from .
     * @return int the number of contacts read from the stream, or -1 when the
     *         file could not be loaded.
     * @throws IOException
     *             when the underlying file stream is corrupt.
     */

    public int reload(InputStream file) throws IOException {
        SearchableSortedLinkedList<ContactEntry> replacement = new SearchableSortedLinkedList<>();
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
