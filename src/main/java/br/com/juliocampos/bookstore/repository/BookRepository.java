package br.com.juliocampos.bookstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.juliocampos.bookstore.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

  Optional<Book> findByTitle(String title);

}
