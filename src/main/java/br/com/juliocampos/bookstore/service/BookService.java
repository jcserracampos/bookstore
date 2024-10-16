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
   * @return
   */
  public List<Book> findAll() {
    return bookRepository.findAll();
  }

  /**
   * @param id
   * @return
   */
  public Optional<Book> findById(Long id) {
    return bookRepository.findById(id);
  }

  /**
   * @param book
   * @return
   */
  public Book save(Book book) {
    return bookRepository.save(book);
  }

  /**
   * @param id
   */
  public void delete(Long id) {
    bookRepository.deleteById(id);
  }
}
