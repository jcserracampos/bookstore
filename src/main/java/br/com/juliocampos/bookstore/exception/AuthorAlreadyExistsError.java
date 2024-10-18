package br.com.juliocampos.bookstore.exception;

public class AuthorAlreadyExistsError extends RuntimeException {
  public AuthorAlreadyExistsError(String message) {
    super(message);
  }
}
