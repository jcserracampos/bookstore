package br.com.juliocampos.bookstore.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.juliocampos.bookstore.model.Author;
import br.com.juliocampos.bookstore.service.AuthorService;
import jakarta.persistence.EntityNotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/authors")
@Tag(name = "Author", description = "Endpoints para gerenciamento de autores")
public class AuthorController {

  @Autowired
  private AuthorService authorService;

  @GetMapping
  @Operation(summary = "Listar todos os autores", description = "Retorna uma lista de todos os autores cadastrados")
  @ApiResponse(responseCode = "200", description = "Operação bem-sucedida")
  public List<Author> findAll() {
    return authorService.findAll();
  }

  @GetMapping("/{id}")
  @Operation(summary = "Buscar um autor por ID", description = "Retorna um autor com base no ID fornecido")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Autor encontrado"),
    @ApiResponse(responseCode = "404", description = "Autor não encontrado")
  })
  public ResponseEntity<Author> findById(@PathVariable Long id) {
    Optional<Author> author = authorService.findById(id);

    return author.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  @Operation(summary = "Criar um novo autor", description = "Cria um novo autor com as informações fornecidas")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Autor criado com sucesso"),
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
  })
  public ResponseEntity<?> save(@RequestBody Author author) {
    try {
      Author savedAuthor = authorService.save(author);
      return ResponseEntity.ok(savedAuthor);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("An error occurred: " + e.getMessage());
    }
  }

  @PutMapping("/{id}")
  @Operation(summary = "Atualizar um autor", description = "Atualiza as informações de um autor existente")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Autor atualizado com sucesso"),
    @ApiResponse(responseCode = "404", description = "Autor não encontrado")
  })
  public ResponseEntity<Author> update(@PathVariable Long id, @RequestBody Author authorDetails) {
    try {
      Author updatedAuthor = authorService.update(id, authorDetails);
      return ResponseEntity.ok(updatedAuthor);
    } catch (EntityNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Excluir um autor", description = "Remove um autor com base no ID fornecido")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "204", description = "Autor excluído com sucesso"),
    @ApiResponse(responseCode = "404", description = "Autor não encontrado")
  })
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    authorService.delete(id);

    return ResponseEntity.noContent().build();
  }
}
