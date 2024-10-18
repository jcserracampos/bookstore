package br.com.juliocampos.bookstore.controller;

import br.com.juliocampos.bookstore.model.Author;
import br.com.juliocampos.bookstore.service.AuthorService;
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

@WebMvcTest(AuthorController.class)
@Import(TestSecurityConfig.class)
@ActiveProfiles("test")
public class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    @Autowired
    private ObjectMapper objectMapper;

    private Author testAuthor;

    @BeforeEach
    void setUp() {
        testAuthor = new Author();
        testAuthor.setId(1L);
        testAuthor.setName("Test Author");
    }

    @Test
    public void whenFindAll_thenReturnAuthorList() throws Exception {
        when(authorService.findAll()).thenReturn(Arrays.asList(testAuthor));

        mockMvc.perform(get("/api/authors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(testAuthor.getId()))
                .andExpect(jsonPath("$[0].name").value(testAuthor.getName()));
    }

    @Test
    public void whenFindById_thenReturnAuthor() throws Exception {
        when(authorService.findById(1L)).thenReturn(Optional.of(testAuthor));

        mockMvc.perform(get("/api/authors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testAuthor.getId()))
                .andExpect(jsonPath("$.name").value(testAuthor.getName()));
    }

    @Test
    public void whenFindByIdNotFound_thenReturn404() throws Exception {
        when(authorService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/authors/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenSaveAuthor_thenReturnSavedAuthor() throws Exception {
        when(authorService.save(any(Author.class))).thenReturn(testAuthor);

        mockMvc.perform(post("/api/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testAuthor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testAuthor.getId()))
                .andExpect(jsonPath("$.name").value(testAuthor.getName()));
    }

    @Test
    public void whenUpdateAuthor_thenReturnUpdatedAuthor() throws Exception {
        when(authorService.update(eq(1L), any(Author.class))).thenReturn(testAuthor);

        mockMvc.perform(put("/api/authors/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testAuthor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testAuthor.getId()))
                .andExpect(jsonPath("$.name").value(testAuthor.getName()));
    }

    @Test
    public void whenDeleteAuthor_thenReturn204() throws Exception {
        doNothing().when(authorService).delete(1L);

        mockMvc.perform(delete("/api/authors/1"))
                .andExpect(status().isNoContent());
    }
}
