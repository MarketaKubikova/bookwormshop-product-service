package com.bookwormshop.productservice.service;

import com.bookwormshop.productservice.mapper.BookMapper;
import com.bookwormshop.productservice.model.Book;
import com.bookwormshop.productservice.model.BookDTO;
import com.bookwormshop.productservice.repository.BookRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public List<BookDTO> getAllBooks() {
        log.info("Returning all books from database.");
        return bookRepository.findAll().stream()
                .map(bookMapper::toDTO)
                .toList();
    }

    public BookDTO getBookById(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toDTO)
                .orElseThrow(() -> {
                    log.error("Book with id '{}' not found.", id);
                    throw new RuntimeException("Book not found in database.");
                });
    }

    public BookDTO saveBook(BookDTO bookDTO){
        Book savedBook = bookRepository.save(bookMapper.toDomain(bookDTO));
        log.info("Book '{}' was successfully saved with id '{}'.", savedBook.getName(), savedBook.getId());
        return bookMapper.toDTO(savedBook);
    }

    public void deleteBook(Long id) {
        log.info("Book with id '{}' was successfully deleted from database.", id);
        bookRepository.deleteById(id);
    }
}
