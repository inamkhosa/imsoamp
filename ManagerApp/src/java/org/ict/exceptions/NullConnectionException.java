package org.ict.exceptions;

public class NullConnectionException extends Exception {

  private String message;

  public NullConnectionException() {
    message = new String("Database connection cannot be null");
  }

  public NullConnectionException(String message) {
    this.message = message;
  }

  public String getMessage() {
    return this.message;
  }
}
