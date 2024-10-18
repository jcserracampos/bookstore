package br.com.juliocampos.bookstore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.juliocampos.bookstore.exception.AuthorAlreadyExistsError;
import br.com.juliocampos.bookstore.model.Author;
import br.com.juliocampos.bookstore.repository.AuthorRepository;

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

  public void delete(Long id) {
    authorRepository.deleteById(id);
  }
}
