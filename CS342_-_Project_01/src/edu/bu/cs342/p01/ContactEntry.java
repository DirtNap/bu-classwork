package edu.bu.cs342.p01;

import static edu.bu.cs342.utilities.NullHandling.*;
import java.io.Serializable;
import edu.bu.cs342.utilities.SearchOptionException;
import edu.bu.cs342.utilities.Searchable;

public class ContactEntry implements Serializable, Searchable, Comparable<ContactEntry> {

    private static final long serialVersionUID = 1L;
    protected static final String[] searchKeys = { "name", "email" };
    private final String name;
    private final String display_name;
    private final ContactEmail email;
    private final ContactPhone phone;

    private boolean search(String text, String pattern, Searchable.SearchScope scope) {
        switch (scope) {
        case PARTIAL:
            return (text.toLowerCase().indexOf(pattern.toLowerCase()) != -1);
        case FULL:
            return text.equalsIgnoreCase(pattern);
        default:
            throw new SearchOptionException("Invalid search scope.");
        }
    }

    public ContactEntry(String name, String email, String phone) throws ContactValidationException {
        if (null == name || null == email || null == phone) {
            throw new IllegalArgumentException("ContactEntry requires name, email, and phone.");
        }
        this.name = this.formatAndCapitalizeString(name);
        this.display_name = this.parseName(this.name);
        this.email = new ContactEmail(email);
        this.phone = new ContactPhone(phone);
    }

    private String parseName(String name) throws ContactValidationException {
        String[] names = name.split("\\s+");
        switch (names.length) {
        case 0:
            throw new ContactValidationException("Invalid Name");
        case 1:
            return name;
        default:
            StringBuilder sb = new StringBuilder();
            sb.append(names[names.length - 1]);
            sb.append(",");
            for (int i = 0; i < names.length - 1; ++i) {
                sb.append(" ");
                sb.append(names[i]);
            }
            return sb.toString();
        }
    }

    private String formatAndCapitalizeString(String str) {
        str = str.replace("\\s*", " ").toLowerCase();
        String[] stringParts = str.split("\\W+");
        for (int i = 0; i < stringParts.length; ++i) {
            str = str.replaceFirst(stringParts[i], String.format("%s%s",
                    stringParts[i].substring(0, 1).toUpperCase(), stringParts[i].substring(1)));
        }
        return str;
    }

    public String getName() {
        return this.display_name;
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

    @Override
    public String[] getSearchKeys() {
        return ContactEntry.searchKeys;
    }

    @Override
    public boolean checkSearchResult(String key, String pattern, SearchScope scope) {
        switch (key) {
        case "name":
            return this.search(this.name, this.formatAndCapitalizeString(pattern), scope);
        case "email":
            return this.search(this.getEmail().toString(), pattern, scope);
        default:
            throw new SearchOptionException("Invalid search key.");
        }
    }
}
