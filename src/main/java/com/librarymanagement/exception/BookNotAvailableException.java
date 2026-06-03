package com.librarymanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class BookNotAvailableException extends RuntimeException {
    public BookNotAvailableException(String isbn) {
        super("Book with ISBN " + isbn + " is not available for loan");
    }
}