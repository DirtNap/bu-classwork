package edu.bu.cs342.hw03;

/**
 * 
 * @author Michael Donnelly
 * 
 *         Demonstration of recursive functions
 * 
 */
public class Recursor {

    // Find numeric value by ASCII value
    public static final short ASCII_BASE_NUM = (short) '0';

    /**
     * Repeat a character.
     * 
     * @param c
     *            char the character to repeat.
     * @param n
     *            int the number of repetitions.
     * @return String c repeated n times.
     */
    private static String charRepeater(char c, int n) {
        if (0 >= n) {
            return "";
        }
        return String.format("%c%s", c, Recursor.charRepeater(c, --n));
    }

    public static String getAsterisks(int end) {
        return Recursor.getAsterisks(1, end);
    }

    public static long stringToNumber(String source) {
        return Recursor.stringToNumber(source, 0);
    }

    /**
     * Returns a number equivalent to the inputed String.
     * 
     * @param source
     *            String a string to convert to a number.
     * @param start
     *            The total to prepend the {@code source} with.
     * @return long a number representation of the string. Value will be correct
     *         but unexpected when non-digit characters are in {@code source}.
     */
    public static long stringToNumber(String source, long start) {
        if (null == source || source.length() == 0) {
            return start;
        }
        start *= 10;
        short digit = (short) (((short) source.charAt(0)) - Recursor.ASCII_BASE_NUM);
        start += digit;
        return Recursor.stringToNumber(source.substring(1, source.length()), start);

    }

    /**
     * Return a pyramid of asterisk characters, in landscape orientation.
     * 
     * @param start
     *            int number of asterisks in the shortest column.
     * @param end
     *            int number of asterisks in the tallest column.
     * @return String the pyramid.
     */
    public static String getAsterisks(int start, int end) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s%n", Recursor.charRepeater('*', start)));
        if (end == start) {
            return "";
        }
        sb.append(Recursor.getAsterisks(start + 1, end));
        sb.append(String.format("%s%n", Recursor.charRepeater('*', start)));
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(Recursor.getAsterisks(20));
        System.out.println(Recursor.stringToNumber("123456789012"));
    }
}
