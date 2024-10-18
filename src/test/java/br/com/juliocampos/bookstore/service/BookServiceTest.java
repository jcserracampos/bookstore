package br.com.juliocampos.bookstore.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.juliocampos.bookstore.exception.AuthorNotFoundException;
import br.com.juliocampos.bookstore.exception.BookAlreadyExistsException;
import br.com.juliocampos.bookstore.model.Author;
import br.com.juliocampos.bookstore.model.Book;
import br.com.juliocampos.bookstore.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;

import java.sql.Date;
import java.time.LocalDate;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        List<Book> books = Arrays.asList(
            createBook(1L, "Book 1", createAuthor(1L, "Author 1")),
            createBook(2L, "Book 2", createAuthor(2L, "Author 2"))
        );
        when(bookRepository.findAll()).thenReturn(books);

        List<Book> result = bookService.findAll();

        assertEquals(2, result.size());
        assertEquals("Book 1", result.get(0).getTitle());
        assertEquals("Book 2", result.get(1).getTitle());
    }

    @Test
    void testFindById() {
        Book book = createBook(1L, "Test Book", createAuthor(1L, "Test Author"));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Optional<Book> result = bookService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("Test Book", result.get().getTitle());
    }

    @Test
    void testSave_Success() {
        Author author = createAuthor(1L, "Test Author");
        Book book = createBook(null, "New Book", author);

        when(bookRepository.findByTitle("New Book")).thenReturn(Optional.empty());
        when(authorService.findById(1L)).thenReturn(Optional.of(author));
        when(bookRepository.save(any(Book.class))).thenReturn(createBook(1L, "New Book", author));

        Book result = bookService.save(book);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("New Book", result.getTitle());
        assertEquals("Test Author", result.getAuthor().getName());
    }

    @Test
    void testSave_BookAlreadyExists() {
        Author author = createAuthor(1L, "Test Author");
        Book book = createBook(null, "Existing Book", author);

        when(bookRepository.findByTitle("Existing Book")).thenReturn(Optional.of(book));

        assertThrows(BookAlreadyExistsException.class, () -> bookService.save(book));
    }

    @Test
    void testSave_AuthorNotFound() {
        Author author = createAuthor(1L, "Non-existent Author");
        Book book = createBook(null, "New Book", author);

        when(bookRepository.findByTitle("New Book")).thenReturn(Optional.empty());
        when(authorService.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AuthorNotFoundException.class, () -> bookService.save(book));
    }

    @Test
    void testUpdate_Success() {
        Author author = createAuthor(1L, "Test Author");
        Book existingBook = createBook(1L, "Old Title", author);
        Book updatedBook = createBook(1L, "New Title", author);
        updatedBook.setReleaseDate(Date.valueOf(LocalDate.now()));

        when(bookRepository.findById(1L)).thenReturn(Optional.of(existingBook));
        when(authorService.findById(1L)).thenReturn(Optional.of(author));
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);

        Book result = bookService.update(1L, updatedBook);

        assertEquals("New Title", result.getTitle());
        assertEquals(Date.valueOf(LocalDate.now()), result.getReleaseDate());
    }

    @Test
    void testUpdate_BookNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> bookService.update(1L, new Book()));
    }

    @Test
    void testUpdate_AuthorNotFound() {
        Author author = createAuthor(1L, "Test Author");
        Book existingBook = createBook(1L, "Old Title", author);
        Book updatedBook = createBook(1L, "New Title", createAuthor(2L, "New Author"));

        when(bookRepository.findById(1L)).thenReturn(Optional.of(existingBook));
        when(authorService.findById(2L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> bookService.update(1L, updatedBook));
    }

    @Test
    void testDelete() {
        bookService.delete(1L);

        verify(bookRepository, times(1)).deleteById(1L);
    }

    // Métodos auxiliares para criar Author e Book
    private Author createAuthor(Long id, String name) {
        Author author = new Author();
        author.setId(id);
        author.setName(name);
        return author;
    }

    private Book createBook(Long id, String title, Author author) {
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setAuthor(author);
        return book;
    }
}