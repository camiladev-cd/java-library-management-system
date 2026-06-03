package com.librarymanagement.dto;

import com.librarymanagement.enums.LoanStatus;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class LoanDTO {

    public record Request(
            @NotNull(message = "Book ID is required")
            Long bookId,

            @NotNull(message = "Member ID is required")
            Long memberId
    ) {}

    public record Response(
            Long id,
            Long bookId,
            String bookTitle,
            String bookIsbn,
            Long memberId,
            String memberName,
            LocalDate loanDate,
            LocalDate returnDate,
            LoanStatus status
    ) {}
}