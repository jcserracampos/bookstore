package br.com.juliocampos.bookstore.repository;

import br.com.juliocampos.bookstore.model.Author;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

  Optional<Author> findByName(String name);

}
