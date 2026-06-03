package com.librarymanagement.mapper;

import com.librarymanagement.dto.BookDTO;
import com.librarymanagement.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDTO.Response toResponse(Book book);

    Book toEntity(BookDTO.Request request);

    void updateEntity(BookDTO.Request request, @MappingTarget Book book);
}