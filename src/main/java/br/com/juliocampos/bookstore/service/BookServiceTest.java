package br.com.juliocampos.bookstore.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import br.com.juliocampos.bookstore.model.Book;
import br.com.juliocampos.bookstore.repository.BookRepository;

public class BookServiceTest {

  @Mock
  private BookRepository bookRepository;

  @InjectMocks
  private BookService bookService;

  @Test
  public void testSave() {
    Book book = new Book();
    book.setTitle("The C Programming Language");
    book.setReleaseDate(Date.valueOf(LocalDate.of(1978, 2, 22)));

    when(bookRepository.save(book)).thenReturn(book);

    Book savedBook = bookService.save(book);

    assertEquals(book.getTitle(), savedBook.getTitle());
    verify(bookRepository, times(1)).save(book);
  }

  @Test
  public void testFindById() {
    Book book = new Book();
    book.setId(1L);
    book.setTitle("The C Programming Language");
    book.setReleaseDate(Date.valueOf(LocalDate.of(1978, 2, 22)));

    when(bookRepository.findById(1L)).thenReturn(java.util.Optional.of(book));

    java.util.Optional<Book> foundBook = bookService.findById(1L);

    assertEquals(book.getTitle(), foundBook.get().getTitle());
    verify(bookRepository, times(1)).findById(1L);
  }
}
