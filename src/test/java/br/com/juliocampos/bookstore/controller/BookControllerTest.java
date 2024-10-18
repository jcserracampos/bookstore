package br.com.juliocampos.bookstore.controller;

import br.com.juliocampos.bookstore.model.Book;
import br.com.juliocampos.bookstore.service.BookService;
import br.com.juliocampos.bookstore.config.TestSecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
@Import(TestSecurityConfig.class)
@ActiveProfiles("test")
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    private Book testBook;

    @BeforeEach
    void setUp() {
        testBook = new Book();
        testBook.setId(1L);
        testBook.setTitle("Test Book");
        // Set other necessary fields
    }

    @Test
    public void whenFindAll_thenReturnBookList() throws Exception {
        when(bookService.findAll()).thenReturn(Arrays.asList(testBook));

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(testBook.getId()))
                .andExpect(jsonPath("$[0].title").value(testBook.getTitle()));
    }

    @Test
    public void whenFindById_thenReturnBook() throws Exception {
        when(bookService.findById(1L)).thenReturn(Optional.of(testBook));

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testBook.getId()))
                .andExpect(jsonPath("$.title").value(testBook.getTitle()));
    }

    @Test
    public void whenFindByIdNotFound_thenReturn404() throws Exception {
        when(bookService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenSaveBook_thenReturnSavedBook() throws Exception {
        when(bookService.save(any(Book.class))).thenReturn(testBook);

        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testBook)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testBook.getId()))
                .andExpect(jsonPath("$.title").value(testBook.getTitle()));
    }

    @Test
    public void whenUpdateBook_thenReturnUpdatedBook() throws Exception {
        when(bookService.update(eq(1L), any(Book.class))).thenReturn(testBook);

        mockMvc.perform(put("/api/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testBook)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testBook.getId()))
                .andExpect(jsonPath("$.title").value(testBook.getTitle()));
    }

    @Test
    public void whenDeleteBook_thenReturn204() throws Exception {
        doNothing().when(bookService).delete(1L);

        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void whenSaveBookWithError_thenReturn400() throws Exception {
        when(bookService.save(any(Book.class))).thenThrow(new RuntimeException("Error saving book"));

        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testBook)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("An error occurred: Error saving book"));
    }

    @Test
    public void whenUpdateBookNotFound_thenReturn404() throws Exception {
        when(bookService.update(eq(1L), any(Book.class))).thenThrow(new jakarta.persistence.EntityNotFoundException());

        mockMvc.perform(put("/api/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testBook)))
                .andExpect(status().isNotFound());
    }
}
