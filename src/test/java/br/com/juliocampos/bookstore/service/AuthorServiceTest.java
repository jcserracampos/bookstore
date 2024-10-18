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

import br.com.juliocampos.bookstore.exception.AuthorAlreadyExistsError;
import br.com.juliocampos.bookstore.model.Author;
import br.com.juliocampos.bookstore.model.Book;
import br.com.juliocampos.bookstore.repository.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;

class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        List<Author> authors = Arrays.asList(createAuthor(1L, "Author 1"), createAuthor(2L, "Author 2"));
        when(authorRepository.findAll()).thenReturn(authors);

        List<Author> result = authorService.findAll();

        assertEquals(2, result.size());
        assertEquals("Author 1", result.get(0).getName());
        assertEquals("Author 2", result.get(1).getName());
    }

    @Test
    void testFindById() {
        Author author = createAuthor(1L, "Test Author");
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        Optional<Author> result = authorService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("Test Author", result.get().getName());
    }

    @Test
    void testSave_Success() {
        Author author = createAuthor(null, "New Author");
        when(authorRepository.findByName("New Author")).thenReturn(Optional.empty());
        when(authorRepository.save(any(Author.class))).thenReturn(createAuthor(1L, "New Author"));

        Author result = authorService.save(author);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("New Author", result.getName());
    }

    @Test
    void testSave_AuthorAlreadyExists() {
        Author author = createAuthor(null, "Existing Author");
        when(authorRepository.findByName("Existing Author")).thenReturn(Optional.of(new Author()));

        assertThrows(AuthorAlreadyExistsError.class, () -> authorService.save(author));
    }

    @Test
    void testUpdate_Success() {
        Author existingAuthor = createAuthor(1L, "Old Name");
        Author updatedAuthor = createAuthor(1L, "New Name");
        when(authorRepository.findById(1L)).thenReturn(Optional.of(existingAuthor));
        when(authorRepository.save(any(Author.class))).thenReturn(updatedAuthor);

        Author result = authorService.update(1L, updatedAuthor);

        assertEquals("New Name", result.getName());
    }

    @Test
    void testUpdate_NotFound() {
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> authorService.update(1L, new Author()));
    }

    @Test
    void testDelete() {
        authorService.delete(1L);

        verify(authorRepository, times(1)).deleteById(1L);
    }

    @Test
    void testSave_WithBooks() {
        Author author = createAuthor(null, "Author with Books");
        Book book1 = new Book();
        Book book2 = new Book();
        author.setBooks(Arrays.asList(book1, book2));

        when(authorRepository.findByName("Author with Books")).thenReturn(Optional.empty());
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        Author result = authorService.save(author);

        assertNotNull(result);
        assertEquals(2, result.getBooks().size());
        assertTrue(result.getBooks().stream().allMatch(book -> book.getAuthor() == result));
    }

    // Método auxiliar para criar um Author
    private Author createAuthor(Long id, String name) {
        Author author = new Author();
        author.setId(id);
        author.setName(name);
        return author;
    }
}