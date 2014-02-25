package edu.bu.cs342.utilities;

public class SearchOptionException extends RuntimeException {
  public SearchOptionException() {
  }
  public SearchOptionException(String message) {
    super(message);
  }

  public SearchOptionException(Throwable cause) {
    super(cause);
  }

  public SearchOptionException(String message, Throwable cause) {
    super(message, cause);
  }

  public SearchOptionException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
