package br.com.juliocampos.bookstore.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import br.com.juliocampos.bookstore.model.Author;
import br.com.juliocampos.bookstore.service.AuthorService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/authors")
@Tag(name = "Author", description = "Endpoints para gerenciamento de autores")
public class AuthorController {

  private static final Logger logger = LoggerFactory.getLogger(AuthorController.class);

  @Autowired
  private AuthorService authorService;

  @GetMapping
  @Operation(summary = "Listar todos os autores", description = "Retorna uma lista de todos os autores cadastrados")
  @ApiResponse(responseCode = "200", description = "Operação bem-sucedida")
  public List<Author> findAll() {
    logger.info("Buscando todos os autores");
    return authorService.findAll();
  }

  @GetMapping("/{id}")
  @Operation(summary = "Buscar um autor por ID", description = "Retorna um autor com base no ID fornecido")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Autor encontrado"),
    @ApiResponse(responseCode = "404", description = "Autor não encontrado")
  })
  public ResponseEntity<Author> findById(@PathVariable Long id) {
    logger.info("Buscando autor com ID: {}", id);
    Optional<Author> author = authorService.findById(id);

    return author.map(value -> {
      logger.info("Autor encontrado: {}", value);
      return ResponseEntity.ok(value);
    }).orElseGet(() -> {
      logger.warn("Autor não encontrado com ID: {}", id);
      return ResponseEntity.notFound().build();
    });
  }

  @PostMapping
  @Operation(summary = "Criar um novo autor", description = "Cria um novo autor com as informações fornecidas")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Autor criado com sucesso"),
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
  })
  public ResponseEntity<?> save(@Valid @RequestBody Author author) {
    try {
      logger.info("Criando novo autor: {}", author);
      Author savedAuthor = authorService.save(author);
      logger.info("Autor criado com sucesso: {}", savedAuthor);
      return ResponseEntity.ok(savedAuthor);
    } catch (Exception e) {
      logger.error("Erro ao criar autor: {}", e.getMessage());
      return ResponseEntity.badRequest().body("An error occurred: " + e.getMessage());
    }
  }

  @PutMapping("/{id}")
  @Operation(summary = "Atualizar um autor", description = "Atualiza as informações de um autor existente")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Autor atualizado com sucesso"),
    @ApiResponse(responseCode = "404", description = "Autor não encontrado")
  })
  public ResponseEntity<Author> update(@PathVariable Long id, @Valid @RequestBody Author authorDetails) {
    try {
      logger.info("Atualizando autor com ID: {}", id);
      Author updatedAuthor = authorService.update(id, authorDetails);
      logger.info("Autor atualizado com sucesso: {}", updatedAuthor);
      return ResponseEntity.ok(updatedAuthor);
    } catch (EntityNotFoundException e) {
      logger.warn("Autor não encontrado para atualização com ID: {}", id);
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
    logger.info("Excluindo autor com ID: {}", id);
    authorService.delete(id);
    logger.info("Autor excluído com sucesso");
    return ResponseEntity.noContent().build();
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
        String fieldName = ((FieldError) error).getField();
        String errorMessage = error.getDefaultMessage();
        errors.put(fieldName, errorMessage);
    });
    logger.warn("Erro de validação: {}", errors);
    return ResponseEntity.badRequest().body(errors);
  }
}
