package com.librarymanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class MemberDTO {

    public record Request(
            @NotBlank(message = "Name is required")
            String name,

            @NotBlank(message = "Email is required")
            @Email(message = "Email must be valid")
            String email,

            @NotBlank(message = "Phone is required")
            String phone
    ) {}

    public record Response(
            Long id,
            String name,
            String email,
            String phone
    ) {}
}