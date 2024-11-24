package com.bookwormshop.productservice.controller;

import com.bookwormshop.productservice.model.BookDTO;
import com.bookwormshop.productservice.util.JsonLoader;
import com.bookwormshop.productservice.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.*;

@WebMvcTest(BookController.class)
class BookControllerTest {
    private static final String BASE_URL = "/api/books";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BookService bookService;

    @Test
    void getAllBooks_responseIsOk() throws Exception {
        String request = JsonLoader.loadTestJson("/get_all_books.json");
        when(bookService.getAllBooks()).thenReturn(List.of(book1984, bookDune));

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(request)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(book1984.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value(bookDune.getName()))
        ;

        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    void getBookById_responseIsAccepted() throws Exception {
        String request = JsonLoader.loadTestJson("/get_book_id_4.json");
        when(bookService.getBookById(1L)).thenReturn(book1984);

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(request)
        )
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isMap())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(book1984.getName()))
        ;

        verify(bookService, times(1)).getBookById(anyLong());
    }

    @Test
    void getBookById_responseIsNotFound() throws Exception {
        when(bookService.getBookById(3L)).thenThrow(new RuntimeException("not found"));

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/3")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(bookService, times(1)).getBookById(anyLong());
    }

    @Test
    void createBook_responseIsOk() throws Exception {
        String request = JsonLoader.loadTestJson("/create_book.json");
        when(bookService.saveBook(any(BookDTO.class))).thenReturn(bookFahrenheit);

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(request)
        )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isMap())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(bookFahrenheit.getName()))
        ;

        verify(bookService, times(1)).saveBook(any(BookDTO.class));
    }

    @Test
    void deleteBook_responseIsOk() throws Exception {
        String request = JsonLoader.loadTestJson("/delete_book_id_1.json");
        doNothing().when(bookService).deleteBook(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(request)
        )
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(bookService, times(1)).deleteBook(anyLong());
    }

    private final BookDTO book1984 = new BookDTO(1L, "1984", "George Orwell", "George Orwell’s chilling prophecy about the future.", 9.99);
    private final BookDTO bookDune = new BookDTO(2L, "Dune", "Frank Herbert", "Frank Herbert’s epic masterpiece—a triumph of the imagination and the bestselling science fiction novel of all time.", 11.99);
    private final BookDTO bookFahrenheit = new BookDTO(3L, "Fahrenheit 451", "Ray Bradbury", "One man is determined to set them free.", 12.99);
}
