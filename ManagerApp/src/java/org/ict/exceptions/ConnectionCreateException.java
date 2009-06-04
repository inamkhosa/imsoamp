package org.ict.exceptions;

public class ConnectionCreateException extends Exception {
  private String message;

  public ConnectionCreateException() {
    message = new String("Unable to connect");
  }

  public ConnectionCreateException(String message) {
    this.message = message;
  }

  public String getMessage() {
    return this.message;
  }
}
