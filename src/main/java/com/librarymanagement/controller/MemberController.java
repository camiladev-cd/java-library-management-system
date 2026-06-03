package com.librarymanagement.controller;

import com.librarymanagement.dto.MemberDTO;
import com.librarymanagement.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
@Tag(name = "Members", description = "Member management endpoints")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "List all members — ADMIN only")
    public ResponseEntity<List<MemberDTO.Response>> findAll() {
        return ResponseEntity.ok(memberService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get member by ID")
    public ResponseEntity<MemberDTO.Response> findById(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Register a new member — ADMIN only")
    public ResponseEntity<MemberDTO.Response> create(
            @Valid @RequestBody MemberDTO.Request request) {
        return ResponseEntity.status(201).body(memberService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update a member — ADMIN only")
    public ResponseEntity<MemberDTO.Response> update(
            @PathVariable Long id,
            @Valid @RequestBody MemberDTO.Request request) {
        return ResponseEntity.ok(memberService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a member — ADMIN only")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        memberService.delete(id);
        return ResponseEntity.noContent().build();
    }
}