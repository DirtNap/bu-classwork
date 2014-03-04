package edu.bu.cs342.p01;

import java.io.Serializable;

/**
 * An immutable representation of an email address
 * 
 * @author Michael Donnelly
 * 
 */
public class ContactEmail implements Serializable, Comparable<ContactEmail> {

    private static final long serialVersionUID = 1L;
    public final String userName;
    public final String domain;
    public final String sortKey;

    /**
     * 
     * @param emailAddress
     *            String the email address to be stored.
     * @throws ContactValidationException
     *             when the email address can't be parsed.
     */
    public ContactEmail(String emailAddress) throws ContactValidationException {
        String[] parts = emailAddress.toLowerCase().split("@");
        if (2 != parts.length) {
            throw new ContactValidationException("Invalid email address");
        }
        this.userName = parts[0];
        emailAddress = parts[1];
        if (!emailAddress.matches("^[a-z0-9\\-\\.]+$")) {
            throw new ContactValidationException("Invalid characters");
        }
        parts = emailAddress.split("\\.");
        if (1 == parts.length) {
            throw new ContactValidationException("Invalid internet domain");
        }
        StringBuilder domainSB = new StringBuilder();
        StringBuilder sortKeySB = new StringBuilder();
        sortKeySB.append(String.format("!%s", this.userName));
        for (int i = 0; i < parts.length; ++i) {
            if (0 < domainSB.length()) {
                domainSB.append(".");
                sortKeySB.insert(0, ".");
            }
            domainSB.append(parts[i]);
            sortKeySB.insert(0, parts[i]);
        }
        this.domain = domainSB.toString();
        this.sortKey = sortKeySB.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (null == o) {
            return false;
        }
        if (this == o) {
            return true;
        }
        return this.toString().equals(o.toString());
    }

    /**
     * Compares first according to domain, starting at the top level, then by
     * username.
     * 
     * @see java.lang.Comparable#compareTo(Object)
     */
    @Override
    public int compareTo(ContactEmail o) {
        return this.sortKey.compareTo(o.sortKey);
    }

    @Override
    public String toString() {
        return String.format("%s@%s", this.userName, this.domain);
    }
}
