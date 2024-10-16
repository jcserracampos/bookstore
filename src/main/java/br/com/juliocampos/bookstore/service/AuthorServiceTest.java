package br.com.juliocampos.bookstore.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.juliocampos.bookstore.model.Author;
import br.com.juliocampos.bookstore.repository.AuthorRepository;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {

  @Mock
  private AuthorRepository authorRepository;

  @InjectMocks
  private AuthorService authorService;

  @Test
  public void testSave() {
    Author author = new Author();
    author.setName("Ada Lovelace");

    when(authorRepository.save(author)).thenReturn(author);

    Author savedAuthor = authorService.save(author);

    assertEquals(author.getName(), savedAuthor.getName());
    verify(authorRepository, times(1)).save(author);
  }

  @Test
  public void testFindById() {
    Author author = new Author();
    author.setId(1L);
    author.setName("Ada Lovelace");

    when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

    Optional<Author> foundAuthor = authorService.findById(1L);

    assertTrue(foundAuthor.isPresent());
    verify(authorRepository, times(1)).findById(1L);
  }
}
