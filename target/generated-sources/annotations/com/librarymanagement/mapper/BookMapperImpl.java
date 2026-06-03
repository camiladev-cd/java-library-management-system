package com.librarymanagement.mapper;

import com.librarymanagement.dto.BookDTO;
import com.librarymanagement.enums.BookCategory;
import com.librarymanagement.model.Book;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-03T13:35:18-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.11 (Oracle Corporation)"
)
@Component
public class BookMapperImpl implements BookMapper {

    @Override
    public BookDTO.Response toResponse(Book book) {
        if ( book == null ) {
            return null;
        }

        Long id = null;
        String title = null;
        String author = null;
        String isbn = null;
        BookCategory category = null;
        boolean available = false;

        id = book.getId();
        title = book.getTitle();
        author = book.getAuthor();
        isbn = book.getIsbn();
        category = book.getCategory();
        available = book.isAvailable();

        BookDTO.Response response = new BookDTO.Response( id, title, author, isbn, category, available );

        return response;
    }

    @Override
    public Book toEntity(BookDTO.Request request) {
        if ( request == null ) {
            return null;
        }

        Book.BookBuilder book = Book.builder();

        book.title( request.title() );
        book.author( request.author() );
        book.isbn( request.isbn() );
        book.category( request.category() );

        return book.build();
    }

    @Override
    public void updateEntity(BookDTO.Request request, Book book) {
        if ( request == null ) {
            return;
        }

        book.setTitle( request.title() );
        book.setAuthor( request.author() );
        book.setIsbn( request.isbn() );
        book.setCategory( request.category() );
    }
}
