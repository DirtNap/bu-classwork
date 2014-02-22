package edu.bu.cs342.utilities;

import static edu.bu.cs342.utilities.NullHandling.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import edu.bu.cs342.utilities.NullHandling.NullSortOrder;

public class NullHandlingTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testEqualsWithNull() {
        assertTrue("Nulls equal nulls", equalsWithNull(null, null));
        Object o = new Object();
        assertFalse("Compare object to null", equalsWithNull(o, null));
        assertFalse("Compare null to object", equalsWithNull(null, o));
    }

    @Test
    public void testStringUnlessNullObject() {
        Object o = null;
        assertEquals("Empty string for null", "", stringUnlessNull(o));
        o = new Object();
        assertEquals("Valid string for object", o.toString(), stringUnlessNull(o));
    }

    @Test
    public void testStringUnlessNullObjectString() {
        Object o = null;
        String format = "-%s-";
        assertEquals("Empty string for null", "", stringUnlessNull(o, format));
        o = new Object();
        assertEquals("Valid string for object", String.format(format, o.toString()),
                stringUnlessNull(o, format));
    }

    @Test
    public void testCompareListWithNulls() {
        assertEquals("Nulls do not interrupt normal sorting", -1,
                compareListWithNulls("a", "a", null, null, "b", "c"));
    }

    @Test
    public void testCompareListWithNullsWithSortOrder() {
        assertEquals("Sorting with nulls first", -1,
                compareListWithNulls(NullSortOrder.NULLS_FIRST, null, "a"));
        assertEquals("Sorting with nulls last", 1,
                compareListWithNulls(NullSortOrder.NULLS_LAST, null, "a"));
    }
}
