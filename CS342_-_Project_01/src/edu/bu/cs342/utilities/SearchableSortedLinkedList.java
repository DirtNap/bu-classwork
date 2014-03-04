package edu.bu.cs342.utilities;

import java.util.ArrayList;
import java.util.List;
import edu.bu.cs342.utilities.Searchable.SearchScope;

/**
 * A {@link SortedLinkedList} of elements which implement {@link Searchable}.
 * 
 * 
 * @author Michael Donnelly
 * 
 * @param <E>
 *            Type Type of list elements, implementing {@code Comparable<E>} and
 *            {@code Searchable}.
 * 
 * @see java.lang.Comparable
 */
public class SearchableSortedLinkedList<E extends Comparable<E> & Searchable> extends
        SortedLinkedList<E> {

    public SearchableSortedLinkedList() {
        this(true);
    }

    public SearchableSortedLinkedList(boolean allowDuplicates) {
        super(allowDuplicates);
    }

    /**
     * Return a {@code List<E>} of {@code Searchable} elements of type {@code E}
     * which satisfy
     * {@link Searchable#checkSearchResult(String, String, SearchScope)}.
     * 
     * @param key
     *            String the search key to validate
     * @param pattern
     *            String the pattern to search for
     * @param scope
     *            Searchable.SearchScope match fully or partially
     * @return List<#> the elements which satisfy the search
     */
    public List<E> search(String key, String pattern, SearchScope scope) {
        ArrayList<E> result = new ArrayList<>();
        for (E element : this) {
            if (element.checkSearchResult(key, pattern, scope)) {
                result.add(element);
            }
        }
        return result;
    }

    /**
     * Remove the elements that would be returned by {@code search()}.
     * 
     * @param key
     *            String the search key to validate
     * @param pattern
     *            String the pattern to search for
     * @param scope
     *            Searchable.SearchScope match fully or partially
     * @return boolean if elements were removed.
     * @throws SearchOptionException
     *             if the elements found can not be removed.
     */
    public boolean removeBySearch(String key, String pattern, SearchScope scope) {
        List<E> elements = this.search(key, pattern, scope);
        for (E element : elements) {
            if (!this.removeItem(element)) {
                throw new SearchOptionException("Unable to remove searched element.");
            }
        }
        return (elements.size() > 0);
    }
}
