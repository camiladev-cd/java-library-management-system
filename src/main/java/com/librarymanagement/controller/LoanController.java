package com.librarymanagement.controller;

import com.librarymanagement.dto.LoanDTO;
import com.librarymanagement.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/loans")
@RequiredArgsConstructor
@Tag(name = "Loans", description = "Loan management endpoints")
public class LoanController {

    private final LoanService loanService;

    @GetMapping
    @Operation(summary = "List all loans")
    public ResponseEntity<List<LoanDTO.Response>> findAll() {
        return ResponseEntity.ok(loanService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get loan by ID")
    public ResponseEntity<LoanDTO.Response> findById(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.findById(id));
    }

    @GetMapping("/member/{memberId}")
    @Operation(summary = "Get loans by member")
    public ResponseEntity<List<LoanDTO.Response>> findByMember(
            @PathVariable Long memberId) {
        return ResponseEntity.ok(loanService.findByMember(memberId));
    }

    @PostMapping
    @Operation(summary = "Create a new loan")
    public ResponseEntity<LoanDTO.Response> create(
            @Valid @RequestBody LoanDTO.Request request) {
        return ResponseEntity.status(201).body(loanService.create(request));
    }

    @PatchMapping("/{id}/return")
    @Operation(summary = "Return a book")
    public ResponseEntity<LoanDTO.Response> returnBook(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.returnBook(id));
    }
}