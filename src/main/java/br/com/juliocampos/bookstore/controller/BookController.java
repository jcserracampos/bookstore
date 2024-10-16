package br.com.juliocampos.bookstore.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.juliocampos.bookstore.model.Book;
import br.com.juliocampos.bookstore.service.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {

  @Autowired
  private BookService bookService;

  @GetMapping
  public List<Book> findAll() {
    return bookService.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Book> findById(@PathVariable Long id) {
    Optional<Book> book = bookService.findById(id);

    return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public Book save(@RequestBody Book book) {
    return bookService.save(book);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    bookService.delete(id);

    return ResponseEntity.noContent().build();
  }
}
