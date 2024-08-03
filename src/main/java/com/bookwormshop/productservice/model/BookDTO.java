package com.bookwormshop.productservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookDTO {
    private Long id;
    private String name;
    private String author;
    private String description;
    private double price;
}
