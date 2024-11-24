package com.bookwormshop.productservice.service;

import com.bookwormshop.productservice.mapper.BookMapper;
import com.bookwormshop.productservice.model.Book;
import com.bookwormshop.productservice.model.BookDTO;
import com.bookwormshop.productservice.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;
    @InjectMocks
    private BookService bookService;

    @Test
    void getAllBooks() {
        when(bookMapper.toDTO(book)).thenReturn(bookDTO);
        when(bookRepository.findAll()).thenReturn(List.of(book));

        List<BookDTO> result = bookService.getAllBooks();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Kytice");
        assertThat(result.get(0).getAuthor()).isEqualTo("Karel Jaromír Erben");
        assertThat(result.get(0).getDescription()).isEqualTo("Sbírka baladických básní K. J. Erbena.");
        assertThat(result.get(0).getPrice()).isEqualTo(99.9);
    }

    @Test
    void getBookById_validId_shouldReturnBook() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookMapper.toDTO(book)).thenReturn(bookDTO);

        BookDTO result = bookService.getBookById(1L);

        assertThat(result.getName()).isEqualTo("Kytice");
        assertThat(result.getAuthor()).isEqualTo("Karel Jaromír Erben");
        assertThat(result.getDescription()).isEqualTo("Sbírka baladických básní K. J. Erbena.");
        assertThat(result.getPrice()).isEqualTo(99.9);
    }

    @Test
    void getBookById_nonExistingId_shouldThrowException() {
        when(bookRepository.findById(666L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> bookService.getBookById(666L));
    }

    @Test
    void saveBook() {
        BookDTO newBookDTO = new BookDTO(null, "Kytice", "Karel Jaromír Erben", "Sbírka baladických básní K. J. Erbena.", 99.9);
        Book bookToSave = new Book(null, "Kytice", "Karel Jaromír Erben", "Sbírka baladických básní K. J. Erbena.", 99.9);

        when(bookMapper.toDomain(newBookDTO)).thenReturn(bookToSave);
        when(bookRepository.save(bookToSave)).thenReturn(book);

        when(bookMapper.toDTO(book)).thenReturn(bookDTO);

        BookDTO result = bookService.saveBook(newBookDTO);

        assertThat(result).isEqualTo(bookDTO);
    }

    @Test
    void deleteBook() {
        bookService.deleteBook(1L);

        verify(bookRepository).deleteById(1L);
    }

    Book book = new Book(1L, "Kytice", "Karel Jaromír Erben", "Sbírka baladických básní K. J. Erbena.", 99.9);
    BookDTO bookDTO = new BookDTO(1L, "Kytice", "Karel Jaromír Erben", "Sbírka baladických básní K. J. Erbena.", 99.9);
}
