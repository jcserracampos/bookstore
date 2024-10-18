package br.com.juliocampos.bookstore.repository;

import br.com.juliocampos.bookstore.model.Author;
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
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void whenFindByName_thenReturnAuthor() {
        // given
        Author author = new Author();
        author.setName("George Orwell");
        authorRepository.save(author);

        // when
        Optional<Author> found = authorRepository.findByName(author.getName());

        // then
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo(author.getName());
    }

    @Test
    public void whenFindByNameNotExist_thenReturnEmpty() {
        // when
        Optional<Author> notFound = authorRepository.findByName("Nome Inexistente");

        // then
        assertThat(notFound).isEmpty();
    }
}
