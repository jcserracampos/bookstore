package br.com.juliocampos.bookstore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.juliocampos.bookstore.exception.AuthorAlreadyExistsError;
import br.com.juliocampos.bookstore.model.Author;
import br.com.juliocampos.bookstore.repository.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class AuthorService {

  @Autowired
  private AuthorRepository authorRepository;

  public List<Author> findAll() {
    return authorRepository.findAll();
  }

  public Optional<Author> findById(Long id) {
    return authorRepository.findById(id);
  }

  public Author save(Author author) {
    Optional<Author> existingAuthor = authorRepository.findByName(author.getName());
    if (existingAuthor.isPresent()) {
      throw new AuthorAlreadyExistsError("Author with name " + author.getName() + " already exists");
    }

    if (author.getBooks() != null) {
      author.getBooks().forEach(book -> book.setAuthor(author));
    }

    return authorRepository.save(author);
  }

  public Author update(Long id, Author authorUpdate) {
    Optional<Author> existingAuthor = authorRepository.findById(id);

    if (existingAuthor.isPresent()) {
      Author author = existingAuthor.get();
      author.setName(authorUpdate.getName());

      if (authorUpdate.getBooks() != null) {
        authorUpdate.getBooks().forEach(book -> book.setAuthor(author));
        author.setBooks(authorUpdate.getBooks());
      }

      return authorRepository.save(author);
    } else {
      throw new EntityNotFoundException("Author not found"); // Lançar uma exceção se não encontrado
    }
  }

  public void delete(Long id) {
    authorRepository.deleteById(id);
  }
}
