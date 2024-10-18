package br.com.juliocampos.bookstore.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.juliocampos.bookstore.model.Book;
import br.com.juliocampos.bookstore.service.BookService;
import jakarta.persistence.EntityNotFoundException;

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
  public ResponseEntity<?> save(@RequestBody Book book) {
    try {
      Book savedBook = bookService.save(book);
      return ResponseEntity.ok(savedBook);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("An error occurred: " + e.getMessage());
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<Book> update(@PathVariable Long id, @RequestBody Book bookDetails) {
    try {
      Book updatedBook = bookService.update(id, bookDetails);
      return ResponseEntity.ok(updatedBook);
    } catch (EntityNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    bookService.delete(id);

    return ResponseEntity.noContent().build();
  }
}
