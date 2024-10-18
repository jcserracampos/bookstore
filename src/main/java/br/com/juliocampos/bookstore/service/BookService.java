package br.com.juliocampos.bookstore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.juliocampos.bookstore.exception.AuthorNotFoundException;
import br.com.juliocampos.bookstore.exception.BookAlreadyExistsException;
import br.com.juliocampos.bookstore.model.Author;
import br.com.juliocampos.bookstore.model.Book;
import br.com.juliocampos.bookstore.repository.BookRepository;

@Service
public class BookService {

  @Autowired
  private BookRepository bookRepository;

  @Autowired AuthorService authorService;

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
    // Check if title already exists
    Optional<Book> existingBook = bookRepository.findByTitle(book.getTitle());
    if (existingBook.isPresent() && book.getAuthor().getId() == existingBook.get().getAuthor().getId()) {
      throw new BookAlreadyExistsException("Book with title " + book.getTitle() + " already exists for this author");
    }

    if (book.getAuthor() != null && book.getAuthor().getId() != null) {
      Optional<Author> existingAuthor = authorService.findById(book.getAuthor().getId());
      if (existingAuthor.isPresent()) {
        book.setAuthor(existingAuthor.get());
      } else {
        throw new AuthorNotFoundException("Author with ID " + book.getAuthor().getId() + " not found");
      }
    }

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
