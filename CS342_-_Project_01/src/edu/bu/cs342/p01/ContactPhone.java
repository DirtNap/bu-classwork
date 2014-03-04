package edu.bu.cs342.p01;

import static edu.bu.cs342.utilities.NullHandling.*;
import java.io.Serializable;
import edu.bu.cs342.utilities.NullHandling.NullSortOrder;

/**
 * An immutable representation of a US phone number.
 * 
 * @author Michael Donnelly
 * 
 */
public class ContactPhone implements Serializable, Comparable<ContactPhone> {

    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object o) {
        if (null == o) {
            return false;
        }
        if (this == o) {
            return true;
        }
        try {
            ContactPhone test = (ContactPhone) o;
            return (equalsWithNull(this.areaCode, test.areaCode)
                    && equalsWithNull(this.exchange, test.exchange) && equalsWithNull(this.number,
                        test.number));
        } catch (ClassCastException ex) {
            return false;
        }
    }

    public final String areaCode;
    public final String exchange;
    public final String number;
    public final String extension;

    /**
     * 
     * @param phoneNumber
     *            String the US phone number to be stored.
     * @throws ContactValidationException
     *             when the phone number can't be parsed.
     */
    public ContactPhone(String phoneNumber) throws ContactValidationException {
        String[] parts = phoneNumber.split("\\s*x\\s*");
        for (int i = 0; i < parts.length; ++i) {
            parts[i] = parts[i].replaceAll("\\D+", "");
        }
        String phoneAreaCode = null, phoneExtension = null;
        switch (parts.length) {
        case 2:
            phoneExtension = parts[1];
        case 1:
            phoneNumber = parts[0];
            break;
        default:
            throw new ContactValidationException(phoneNumber, "Invalid Extension");
        }
        switch (phoneNumber.length()) {
        case 7:
        case 10:
            break;
        case 11:
            if ('1' == phoneNumber.charAt(0)) {
                phoneNumber = phoneNumber.substring(1);
                break;
            }
        default:
            throw new ContactValidationException(phoneNumber, "Invalid US Phone Number");
        }
        if (10 == phoneNumber.length()) {
            phoneAreaCode = phoneNumber.substring(0, 3);
        }
        this.number = phoneNumber.substring(phoneNumber.length() - 4);
        this.exchange = phoneNumber.substring(phoneNumber.length() - 7, phoneNumber.length() - 4);
        this.areaCode = phoneAreaCode;
        this.extension = phoneExtension;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(stringUnlessNull(this.areaCode, "(%s) "));
        result.append(String.format("%s-%s", this.exchange, this.number));
        result.append(stringUnlessNull(this.extension, "x%s"));
        return result.toString();
    }

    @Override
    public int compareTo(ContactPhone o) {
        int result = compareListWithNulls(this.areaCode, o.areaCode, this.exchange, o.exchange,
                this.number, o.number);
        if (0 == result) {
            result = compareListWithNulls(NullSortOrder.NULLS_FIRST, this.extension, o.extension);
        }
        return result;
    }
}
