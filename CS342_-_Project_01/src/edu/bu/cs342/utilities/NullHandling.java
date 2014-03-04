package edu.bu.cs342.utilities;

/**
 * 
 * @author Michael Donnelly
 * 
 *         Contains static functions for handling null data in common
 *         situations.
 * 
 */
public class NullHandling {

    /**
     * 
     * @author Michael Donnelly
     * 
     *         Determines whether nulls in sorted collections drift to the
     *         beginning or end of the collection.
     * 
     */
    public enum NullSortOrder {
        NULLS_FIRST(-1), NULLS_LAST(1);
        private int comparator;

        NullSortOrder(int comparator) {
            this.comparator = comparator;
        }

        public int comparator() {
            return this.comparator;
        }
    }

    /**
     * Eliminates null pointer exceptions in Object equality operations.
     * 
     * @param o1
     *            Object first object
     * @param o2
     *            Object second object
     * @return boolean {@code true} if both objects are {@code null}, or else
     *         returns {@code o1.equals(o2)}
     */
    public static boolean equalsWithNull(Object o1, Object o2) {
        if (null == o1) {
            return (null == o2);
        } else {
            return o1.equals(o2);
        }
    }

    public static String stringUnlessNull(Object o) {
        if (null != o) {
            return stringUnlessNull(o.toString(), "%s");
        }
        return "";
    }

    /**
     * Return a string representation of an object, or the empty string if null
     * 
     * @param o
     *            Object the object to represent as a String
     * @param format
     *            String optional format string
     * @return String the string representation of the object;
     */
    public static String stringUnlessNull(Object o, String format) {
        if (null != o) {
            return String.format(format, o);
        }
        return "";
    }

    @SuppressWarnings({ "rawtypes" })
    public static int compareListWithNulls(Comparable... objects) {
        return compareListWithNulls(NullSortOrder.NULLS_LAST, objects);
    }

    /**
     * Compare a list of pairs of Objects.
     * 
     * The first {@code Comparable} object is compared to the second object. If
     * both are null, or if {@code Comparable.compareTo} returns 0, the
     * comparison moves to the next pair. If one object is null but the other
     * isn't, the value returned is as per {@code NullSortOrder}. If neither
     * value is null, and the value of compareTo is non-zero or it is the final
     * pair in the argument list, the raw value is returned.
     * 
     * @param order
     *            NullSortOrder indicates whether nulls come before or after
     *            non-null values.
     * @param objects
     *            Comparable a sequence of objects to be compared.
     * @return int A number below 0 indicates that the even-indexed items should
     *         be placed before the odd-indexed items. A number above 0 means
     *         the reverse. 0 indicates equality.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static int compareListWithNulls(NullSortOrder order, Comparable... objects) {
        if (objects.length == 0 || objects.length % 2 != 0) {
            throw new IllegalArgumentException();
        }
        for (int i = 0, j = 1; j < objects.length; i += 2, j += 2) {
            if (null == objects[i]) {
                if (null != objects[j]) {
                    return order.comparator();
                }
            }
            if (null == objects[j]) {
                return -1 * order.comparator();
            }
            if (objects[i].compareTo(objects[j]) != 0) {
                return objects[i].compareTo(objects[j]);
            }
        }
        return 0;
    }
}
