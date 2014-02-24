package edu.bu.cs342.p01;

import static edu.bu.cs342.utilities.NullHandling.*;
import java.io.Serializable;

public class ContactEntry implements Serializable, Comparable<ContactEntry> {

    private static final long serialVersionUID = 1L;
    private final String name;
    private final ContactEmail email;
    private final ContactPhone phone;

    public ContactEntry(String name, String email, String phone) throws ContactValidationException {
        if (null == name || null == email || null == phone) {
            throw new IllegalArgumentException("ContactEntry requires name, email, and phone.");
        }
        this.name = this.parseName(name);
        this.email = new ContactEmail(email);
        this.phone = new ContactPhone(phone);
    }

    protected String parseName(String name) throws ContactValidationException {
        String[] names = name.split("\\s+");
        if (names.length <= 1) {
            return name;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(names[names.length - 1]);
        sb.append(",");
        for (int i = 0; i < names.length - 1; ++i) {
            sb.append(" ");
            sb.append(names[i]);
        }
        return sb.toString();
    }

    public String getName() {
        return this.name;
    }

    public ContactEmail getEmail() {
        return this.email;
    }

    public ContactPhone getPhone() {
        return this.phone;
    }

    @Override
    public String toString() {
        return String.format("%s <%s> %s", this.getName(), this.getEmail(), this.getPhone());
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
            ContactEntry test = (ContactEntry) o;
            return this.getName().equalsIgnoreCase(test.getName())
                    && this.getEmail().equals(test.getEmail())
                    && this.getPhone().equals(test.getPhone());
        } catch (ClassCastException ex) {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public int compareTo(ContactEntry o) {
        return compareListWithNulls(this.getName(), o.getName(), this.getEmail(), o.getEmail(),
                this.getPhone(), o.getPhone());
    }
}