package br.com.juliocampos.bookstore.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.juliocampos.bookstore.model.Author;
import br.com.juliocampos.bookstore.service.AuthorService;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

  @Autowired
  private AuthorService authorService;

  @GetMapping
  public List<Author> findAll() {
    return authorService.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Author> findById(@PathVariable Long id) {
    Optional<Author> author = authorService.findById(id);

    return author.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<?> save(@RequestBody Author author) {
    try {
      Author savedAuthor = authorService.save(author);
      return ResponseEntity.ok(savedAuthor);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("An error occurred: " + e.getMessage());
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<Author> update(@PathVariable Long id, @RequestBody Author authorDetails) {
    try {
      Author updatedAuthor = authorService.update(id, authorDetails);
      return ResponseEntity.ok(updatedAuthor);
    } catch (EntityNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    authorService.delete(id);

    return ResponseEntity.noContent().build();
  }
}
