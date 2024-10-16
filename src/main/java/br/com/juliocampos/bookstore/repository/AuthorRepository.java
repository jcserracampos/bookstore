package br.com.juliocampos.bookstore.repository;

import br.com.juliocampos.bookstore.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
  
}
