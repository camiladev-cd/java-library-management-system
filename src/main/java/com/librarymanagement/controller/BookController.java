package com.librarymanagement.controller;

import com.librarymanagement.dto.BookDTO;
import com.librarymanagement.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
@Tag(name = "Books", description = "Book management endpoints")
public class BookController {

    private final BookService bookService;

    @GetMapping
    @Operation(summary = "List all books")
    public ResponseEntity<List<BookDTO.Response>> findAll() {
        return ResponseEntity.ok(bookService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get book by ID")
    public ResponseEntity<BookDTO.Response> findById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.findById(id));
    }

    @GetMapping("/available")
    @Operation(summary = "List available books")
    public ResponseEntity<List<BookDTO.Response>> findAvailable() {
        return ResponseEntity.ok(bookService.findAvailable());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Register a new book — ADMIN only")
    public ResponseEntity<BookDTO.Response> create(
            @Valid @RequestBody BookDTO.Request request) {
        return ResponseEntity.status(201).body(bookService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update a book — ADMIN only")
    public ResponseEntity<BookDTO.Response> update(
            @PathVariable Long id,
            @Valid @RequestBody BookDTO.Request request) {
        return ResponseEntity.ok(bookService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a book — ADMIN only")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}