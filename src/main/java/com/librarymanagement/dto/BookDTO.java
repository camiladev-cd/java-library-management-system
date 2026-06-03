package com.librarymanagement.dto;

import com.librarymanagement.enums.BookCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BookDTO {

    public record Request(
            @NotBlank(message = "Title is required")
            String title,

            @NotBlank(message = "Author is required")
            String author,

            @NotBlank(message = "ISBN is required")
            String isbn,

            @NotNull(message = "Category is required")
            BookCategory category
    ) {}

    public record Response(
            Long id,
            String title,
            String author,
            String isbn,
            BookCategory category,
            boolean available
    ) {}
}