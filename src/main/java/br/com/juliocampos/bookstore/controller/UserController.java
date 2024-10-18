package br.com.juliocampos.bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.juliocampos.bookstore.model.User;
import br.com.juliocampos.bookstore.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User", description = "Endpoints para gerenciamento de usuários")
public class UserController {

  @Autowired
  private UserService userService;

  @PostMapping
  @Operation(summary = "Criar um novo usuário", description = "Cria um novo usuário com as informações fornecidas")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Usuário criado com sucesso"),
    @ApiResponse(responseCode = "400", description = "Dados inválidos ou usuário já existente")
  })
  public ResponseEntity<?> createUser(@RequestBody User user) {
    try {
      User createdUser = userService.createUser(user);
      // Criando um novo objeto User sem a senha
      User responseUser = new User();
      responseUser.setId(createdUser.getId());
      responseUser.setUsername(createdUser.getUsername());
      // Adicione outros campos necessários, exceto a senha
      return ResponseEntity.ok(responseUser);
    } catch (IllegalArgumentException e) {
      // 400 Bad Request para usuário já existente evitando exploração de vulnerabilidade de enumeração de usuários
      return ResponseEntity.badRequest().build();
    }
  }
}
