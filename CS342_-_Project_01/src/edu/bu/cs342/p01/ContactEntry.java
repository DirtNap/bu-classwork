package edu.bu.cs342.p01;

import java.io.Serializable;

public class ContactEntry implements Serializable, Comparable<ContactEntry> {

    private static final long serialVersionUID = 1L;
    private final String name;
    private final String email;
    private final String phone;

    public ContactEntry(String name, String email, String phone) {
        if (null == name || null == email || null == phone) {
            throw new IllegalArgumentException("ContactEntry requires name, email, and phone.");
        }
        this.name = this.parseName(name);
        this.email = email;
        this.phone = phone;
    }

    protected String parseName(String name) {
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

    public String getEmail() {
        return this.email;
    }

    public String getPhone() {
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
                    && this.getEmail().equalsIgnoreCase(test.getEmail())
                    && this.getPhone().equalsIgnoreCase(test.getPhone());
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
        if (null == o) {
            return -1;
        }
        int result = this.getName().compareToIgnoreCase(o.getName());
        if (0 == result) {
            result = this.getEmail().compareToIgnoreCase(o.getEmail());
            if (0 == result) {
                result = this.getPhone().compareToIgnoreCase(o.getPhone());
            }
        }
        return result;
    }
}
