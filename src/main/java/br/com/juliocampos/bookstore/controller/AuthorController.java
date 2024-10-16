package br.com.juliocampos.bookstore.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.juliocampos.bookstore.model.Author;
import br.com.juliocampos.bookstore.service.AuthorService;

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
  public Author save(@RequestBody Author author) {
    return authorService.save(author);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    authorService.delete(id);

    return ResponseEntity.noContent().build();
  }
}
