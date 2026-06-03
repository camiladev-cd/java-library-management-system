package com.librarymanagement.service;

import com.librarymanagement.dto.BookDTO;
import com.librarymanagement.enums.BookCategory;
import com.librarymanagement.exception.DuplicateResourceException;
import com.librarymanagement.exception.ResourceNotFoundException;
import com.librarymanagement.mapper.BookMapper;
import com.librarymanagement.model.Book;
import com.librarymanagement.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("BookService Tests")
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookService bookService;

    private Book book;
    private BookDTO.Request request;
    private BookDTO.Response response;

    @BeforeEach
    void setUp() {
        book = Book.builder()
                .id(1L)
                .title("Clean Code")
                .author("Robert C. Martin")
                .isbn("978-0132350884")
                .category(BookCategory.TECHNOLOGY)
                .available(true)
                .build();

        request = new BookDTO.Request(
                "Clean Code",
                "Robert C. Martin",
                "978-0132350884",
                BookCategory.TECHNOLOGY
        );

        response = new BookDTO.Response(
                1L, "Clean Code", "Robert C. Martin",
                "978-0132350884", BookCategory.TECHNOLOGY, true
        );
    }

    // ── create ────────────────────────────────────────────────────

    @Test
    @DisplayName("Should create book when ISBN is unique")
    void shouldCreateBook_whenIsbnIsUnique() {
        when(bookRepository.existsByIsbn(anyString())).thenReturn(false);
        when(bookMapper.toEntity(any(BookDTO.Request.class))).thenReturn(book);
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(bookMapper.toResponse(any(Book.class))).thenReturn(response);

        BookDTO.Response result = bookService.create(request);

        assertThat(result).isNotNull();
        assertThat(result.isbn()).isEqualTo("978-0132350884");
        assertThat(result.title()).isEqualTo("Clean Code");
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    @DisplayName("Should throw DuplicateResourceException when ISBN already exists")
    void shouldThrowException_whenIsbnAlreadyExists() {
        when(bookRepository.existsByIsbn(anyString())).thenReturn(true);

        assertThatThrownBy(() -> bookService.create(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessageContaining("978-0132350884");

        verify(bookRepository, never()).save(any());
    }

    // ── findById ──────────────────────────────────────────────────

    @Test
    @DisplayName("Should return book when ID exists")
    void shouldReturnBook_whenIdExists() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookMapper.toResponse(book)).thenReturn(response);

        BookDTO.Response result = bookService.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when ID does not exist")
    void shouldThrowException_whenIdNotFound() {
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }

    // ── findAll ───────────────────────────────────────────────────

    @Test
    @DisplayName("Should return all books")
    void shouldReturnAllBooks() {
        when(bookRepository.findAll()).thenReturn(List.of(book));
        when(bookMapper.toResponse(book)).thenReturn(response);

        List<BookDTO.Response> result = bookService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).title()).isEqualTo("Clean Code");
    }

    // ── findAvailable ─────────────────────────────────────────────

    @Test
    @DisplayName("Should return only available books")
    void shouldReturnOnlyAvailableBooks() {
        when(bookRepository.findByAvailableTrue()).thenReturn(List.of(book));
        when(bookMapper.toResponse(book)).thenReturn(response);

        List<BookDTO.Response> result = bookService.findAvailable();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).available()).isTrue();
    }

    // ── delete ────────────────────────────────────────────────────

    @Test
    @DisplayName("Should delete book when ID exists")
    void shouldDeleteBook_whenIdExists() {
        when(bookRepository.existsById(1L)).thenReturn(true);

        bookService.delete(1L);

        verify(bookRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when deleting non-existent book")
    void shouldThrowException_whenDeletingNonExistentBook() {
        when(bookRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> bookService.delete(99L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(bookRepository, never()).deleteById(any());
    }
}