package com.bookwormshop.productservice.service;

import com.bookwormshop.productservice.model.Book;
import com.bookwormshop.productservice.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private BookService bookService;

    @Test
    void getAllBooks() {
        var book = new Book();
        when(bookRepository.findAll()).thenReturn(List.of(book));

        var result = bookService.getAllBooks();

        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(book);
    }
}
