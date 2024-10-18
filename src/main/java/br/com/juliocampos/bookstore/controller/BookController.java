package br.com.juliocampos.bookstore.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.juliocampos.bookstore.model.Book;
import br.com.juliocampos.bookstore.service.BookService;
import jakarta.persistence.EntityNotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Book", description = "Endpoints para gerenciamento de livros")
public class BookController {

  @Autowired
  private BookService bookService;

  @GetMapping
  @Operation(summary = "Listar todos os livros", description = "Retorna uma lista de todos os livros cadastrados")
  @ApiResponse(responseCode = "200", description = "Operação bem-sucedida")
  public List<Book> findAll() {
    return bookService.findAll();
  }

  @GetMapping("/{id}")
  @Operation(summary = "Buscar um livro por ID", description = "Retorna um livro com base no ID fornecido")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Livro encontrado"),
    @ApiResponse(responseCode = "404", description = "Livro não encontrado")
  })
  public ResponseEntity<Book> findById(@PathVariable Long id) {
    Optional<Book> book = bookService.findById(id);

    return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  @Operation(summary = "Criar um novo livro", description = "Cria um novo livro com as informações fornecidas")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Livro criado com sucesso"),
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
  })
  public ResponseEntity<?> save(@RequestBody Book book) {
    try {
      Book savedBook = bookService.save(book);
      return ResponseEntity.ok(savedBook);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("An error occurred: " + e.getMessage());
    }
  }

  @PutMapping("/{id}")
  @Operation(summary = "Atualizar um livro", description = "Atualiza as informações de um livro existente")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Livro atualizado com sucesso"),
    @ApiResponse(responseCode = "404", description = "Livro não encontrado")
  })
  public ResponseEntity<Book> update(@PathVariable Long id, @RequestBody Book bookDetails) {
    try {
      Book updatedBook = bookService.update(id, bookDetails);
      return ResponseEntity.ok(updatedBook);
    } catch (EntityNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Excluir um livro", description = "Remove um livro com base no ID fornecido")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "204", description = "Livro excluído com sucesso"),
    @ApiResponse(responseCode = "404", description = "Livro não encontrado")
  })
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    bookService.delete(id);

    return ResponseEntity.noContent().build();
  }
}
