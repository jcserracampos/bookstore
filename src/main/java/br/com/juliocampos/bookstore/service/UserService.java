package br.com.juliocampos.bookstore.service;

import br.com.juliocampos.bookstore.model.User;
import br.com.juliocampos.bookstore.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        // Define a role como 'USER' automaticamente
        user.setRoles(new HashSet<>(Set.of("ROLE_USER")));

        // Codifica a senha antes de salvar
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }
}
