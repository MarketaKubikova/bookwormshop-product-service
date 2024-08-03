package com.bookwormshop.productservice.service;

import com.bookwormshop.productservice.model.Book;
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

    public List<Book> getAllBooks() {
        log.info("Returning all books from database.");
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Book with id '{}' not found.", id);
                    throw new IllegalArgumentException("Book not found in database.");
                });
    }

    public Book saveBook(Book book){
        Book savedBook = bookRepository.save(book);
        log.info("Book '{}' was successfully saved with id '{}'.", savedBook.getName(), savedBook.getId());
        return savedBook;
    }

    public void deleteBook(Long id) {
        log.info("Book with id '{}' was successfully deleted from database.", id);
        bookRepository.deleteById(id);
    }
}
