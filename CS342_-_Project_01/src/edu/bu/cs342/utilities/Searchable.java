package edu.bu.cs342.utilities;

/**
 * An interface for searching by arbitrary criteria
 */
public interface Searchable {
  public enum SearchScope {
    PARTIAL, FULL;
  }
  public String[] getSearchKeys();
  public boolean getSearchResult(String key, String pattern, SearchScope scope);
}
