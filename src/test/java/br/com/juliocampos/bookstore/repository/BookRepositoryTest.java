package br.com.juliocampos.bookstore.repository;

import br.com.juliocampos.bookstore.model.Book;
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
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void whenFindByTitle_thenReturnBook() {
        // given
        Author author = new Author();
        author.setName("George Orwell");
        authorRepository.save(author);

        Book book = new Book();
        book.setTitle("1984");
        book.setAuthor(author);
        bookRepository.save(book);

        // when
        Optional<Book> found = bookRepository.findByTitle(book.getTitle());

        // then
        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo(book.getTitle());
        assertThat(found.get().getAuthor().getName()).isEqualTo(author.getName());
    }

    @Test
    public void whenFindByTitleNotExist_thenReturnEmpty() {
        // when
        Optional<Book> notFound = bookRepository.findByTitle("Livro Inexistente");

        // then
        assertThat(notFound).isEmpty();
    }

    @Test
    public void whenSaveBook_thenFindById() {
        // given
        Author author = new Author();
        author.setName("J.R.R. Tolkien");
        authorRepository.save(author);

        Book book = new Book();
        book.setTitle("O Senhor dos Anéis");
        book.setAuthor(author);
        Book savedBook = bookRepository.save(book);

        // when
        Optional<Book> found = bookRepository.findById(savedBook.getId());

        // then
        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("O Senhor dos Anéis");
        assertThat(found.get().getAuthor().getName()).isEqualTo("J.R.R. Tolkien");
    }
}
