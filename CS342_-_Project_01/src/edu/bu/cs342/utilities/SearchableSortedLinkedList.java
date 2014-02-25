package edu.bu.cs342.utilities;

import java.util.ArrayList;
import java.util.List;

import edu.bu.cs342.utilities.Searchable.SearchScope;

public class SearchableSortedLinkedList<E extends Comparable<E> & Searchable> extends SortedLinkedList<E> {

  public SearchableSortedLinkedList() {
    this(true);
  }

  public SearchableSortedLinkedList(boolean allowDuplicates) {
    super(allowDuplicates);
  }

  public List<E> search(String key, String pattern, SearchScope scope) {
    ArrayList<E> result = new ArrayList<>();
    for (E element : this) {
      if (element.getSearchResult(key, pattern, scope)) {
        result.add(element);
      }
    }
    return result;
  }
  
  public boolean removeBySearch(String key, String pattern, SearchScope scope) {
    List<E> elements  = this.search(key, pattern, scope);
    for (E element : elements) {
      if (!this.removeItem(element)) {
        throw new SearchOptionException("Unable to remove searched element.");
      }
    }
    return (elements.size() > 0);
  }
}
