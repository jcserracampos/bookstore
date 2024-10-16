package br.com.juliocampos.bookstore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.juliocampos.bookstore.model.Book;
import br.com.juliocampos.bookstore.repository.BookRepository;

@Service
public class BookService {

  @Autowired
  private BookRepository bookRepository;

  /**
   * Retrieve all books from the database
   *
   * @return List<Book>
   */
  public List<Book> findAll() {
    return bookRepository.findAll();
  }

  /**
   * Retrieve a book by its id
   *
   * @param id
   * @return Optional<Book>
   */
  public Optional<Book> findById(Long id) {
    return bookRepository.findById(id);
  }

  /**
   * Save a book to the database
   *
   * @param book
   * @return Book
   */
  public Book save(Book book) {
    return bookRepository.save(book);
  }

  /**
   * Delete a book from the database
   *
   * @param id
   */
  public void delete(Long id) {
    bookRepository.deleteById(id);
  }
}
