package br.com.juliocampos.bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.juliocampos.bookstore.model.User;
import br.com.juliocampos.bookstore.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

  @Autowired
  private UserService userService;

  @PostMapping
  public ResponseEntity<User> createUser(@RequestBody User user) {
    try {
      User createdUser = userService.createUser(user);
      return ResponseEntity.ok(createdUser);
    } catch (IllegalArgumentException e) {
      // 400 Bad Request para usuário já existente evitando exploração de vulnerabilidade de enumeração de usuários
      return ResponseEntity.badRequest().build();
    }
  }
}
