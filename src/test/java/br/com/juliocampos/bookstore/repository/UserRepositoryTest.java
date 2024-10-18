package br.com.juliocampos.bookstore.repository;

import br.com.juliocampos.bookstore.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application.properties")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenFindByUsername_thenReturnUser() {
        // given
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");
        userRepository.save(user);

        // when
        Optional<User> found = userRepository.findByUsername(user.getUsername());

        // then
        assertThat(found).isPresent();
        assertThat(found.get().getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    public void whenFindByUsernameNotExist_thenReturnEmpty() {
        // when
        Optional<User> notFound = userRepository.findByUsername("nonexistentuser");

        // then
        assertThat(notFound).isEmpty();
    }

    @Test
    public void whenSaveUser_thenFindById() {
        // given
        User user = new User();
        user.setUsername("newuser");
        user.setPassword("newpassword");
        User savedUser = userRepository.save(user);

        // when
        Optional<User> found = userRepository.findById(savedUser.getId());

        // then
        assertThat(found).isPresent();
        assertThat(found.get().getUsername()).isEqualTo("newuser");
    }
}
