package edu.bu.cs342.utilities;

public class NullHandling {

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
