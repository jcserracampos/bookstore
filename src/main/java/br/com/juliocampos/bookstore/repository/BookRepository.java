package br.com.juliocampos.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.juliocampos.bookstore.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
