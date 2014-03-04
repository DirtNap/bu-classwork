package edu.bu.cs342.utilities;

/**
 * An interface for searching by arbitrary criteria.
 * 
 * The implementing class exposes a a list of searchable keys. When
 * {@code checkSearchResults} is called, the implementing class validates the
 * pattern against the text represented by the key according to the
 * {@code SearchScope}, and returns the result.
 * 
 * @author Michael Donnelly
 */
public interface Searchable {
    /**
     * 
     * @author dirtnap
     * 
     *         Indicates whether search key terms should match fully or
     *         partially.
     * 
     */
    public enum SearchScope {
        PARTIAL, FULL;
    }

    /**
     * A list of search keys supported by the implementing class.
     * 
     * @return String[] A list of search keys supported by the implementing
     *         class.
     */
    public String[] getSearchKeys();

    /**
     * Returns true if the text represented by key matches pattern according to
     * scope, otherwise false.
     * 
     * @param key
     *            String one of the keys returned by {@code getSearchKeys}
     * @param pattern
     *            String the text to search for.
     * @param scope
     *            SearchScope whether or not the pattern must fully match the
     *            text represented by key
     * @return boolean indicates if the match is successful.
     */
    public boolean checkSearchResult(String key, String pattern, SearchScope scope);
}
