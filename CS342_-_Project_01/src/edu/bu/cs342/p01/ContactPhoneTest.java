package edu.bu.cs342.p01;

import static org.junit.Assert.*;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

public class ContactPhoneTest {

    private String testAreaCode;
    private String testExtension;
    private String testNumber;
    private String testExchange;
    private String[] unsortedNumbers;
    private String[] sortedNubmers;
    private ContactPhone testContactPhone;
    private String testStringContactPhone;

    @Before
    public void setUp() throws Exception {
        this.testAreaCode = "617";
        this.testExchange = "555";
        this.testNumber = "1212";
        this.testExtension = "111";
        this.testStringContactPhone = String.format("(%s) %s-%sx%s", this.testAreaCode,
                this.testExchange, this.testNumber, this.testExtension);
        this.testContactPhone = new ContactPhone(this.testStringContactPhone);
        this.unsortedNumbers = new String[] { "(222) 222-2222", "(221) 222-2222", "(223) 222-2222",
                "(222) 221-2222", "(222) 223-2222", "(222) 223-2221", "(222) 223-2223",
                "(222) 222-2222x222", "(222) 222-2222x223", "(222) 222-2222x221" };
        this.sortedNubmers = new String[this.unsortedNumbers.length];
        for (int i = 0; i < this.unsortedNumbers.length; ++i) {
            this.sortedNubmers[i] = this.unsortedNumbers[i];
        }
        Arrays.sort(this.sortedNubmers);
    }

    @Test
    public void testEqualsObject() throws ContactValidationException {
        ContactPhone localTestContactPhone;
        assertFalse(this.testContactPhone.equals(null));
        assertTrue(this.testContactPhone.equals(this.testContactPhone));
        localTestContactPhone = new ContactPhone(this.testStringContactPhone);
        assertTrue(this.testContactPhone.equals(localTestContactPhone));
        localTestContactPhone = new ContactPhone("(222) 222-2222x2222");
        assertFalse(this.testContactPhone.equals(localTestContactPhone));
    }

    @Test
    public void testContactPhone() throws ContactValidationException {
        assertEquals(this.testAreaCode, this.testContactPhone.areaCode);
        assertEquals(this.testExchange, this.testContactPhone.exchange);
        assertEquals(this.testNumber, this.testContactPhone.number);
        assertEquals(this.testExtension, this.testContactPhone.extension);
    }

    @Test
    public void testToString() {
        assertEquals(this.testStringContactPhone, this.testContactPhone.toString());
    }

    @Test
    public void testCompareTo() {
        ContactPhone[] localTestContactPhones = new ContactPhone[this.unsortedNumbers.length];
        for (int i = 0; i < this.unsortedNumbers.length; ++i) {
            try {
                localTestContactPhones[i] = new ContactPhone(this.unsortedNumbers[i]);
            } catch (ContactValidationException ex) {
                ;
            }
        }
        Arrays.sort(localTestContactPhones);
        for (int i = 0; i < this.sortedNubmers.length; ++i) {
            assertEquals("Object and string sorting match", this.sortedNubmers[i],
                    localTestContactPhones[i].toString());
        }
    }
}
