package com.librarymanagement.mapper;

import com.librarymanagement.dto.LoanDTO;
import com.librarymanagement.model.Loan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LoanMapper {

    @Mapping(source = "book.id",     target = "bookId")
    @Mapping(source = "book.title",  target = "bookTitle")
    @Mapping(source = "book.isbn",   target = "bookIsbn")
    @Mapping(source = "member.id",   target = "memberId")
    @Mapping(source = "member.name", target = "memberName")
    LoanDTO.Response toResponse(Loan loan);
}