package com.bookwormshop.productservice.mapper;

import com.bookwormshop.productservice.model.Book;
import com.bookwormshop.productservice.model.BookDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {
    Book toDomain(BookDTO bookDTO);
    BookDTO toDTO(Book book);
}
