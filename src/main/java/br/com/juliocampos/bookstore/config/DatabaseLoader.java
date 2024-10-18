package br.com.juliocampos.bookstore.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.juliocampos.bookstore.model.User;
import br.com.juliocampos.bookstore.repository.UserRepository;

@Component
public class DatabaseLoader implements CommandLineRunner {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public void run(String... args) throws Exception {
    if (userRepository.findByUsername("admin").isEmpty()) {
      User admin = new User();
      admin.setUsername("admin");
      admin.setPassword(passwordEncoder.encode("admin123"));
      admin.setRoles(new HashSet<>(Set.of("ROLE_ADMIN")));
      userRepository.save(admin);
    }

  if (userRepository.findByUsername("user").isEmpty()) {
    User user = new User();
    user.setUsername("user");
    user.setPassword(passwordEncoder.encode("user123"));
    user.setRoles(new HashSet<>(Set.of("ROLE_USER")));
    userRepository.save(user);
    }
  }
}
