package com.librarymanagement.service;

import com.librarymanagement.dto.LoanDTO;
import com.librarymanagement.enums.BookCategory;
import com.librarymanagement.enums.LoanStatus;
import com.librarymanagement.exception.BookNotAvailableException;
import com.librarymanagement.exception.ResourceNotFoundException;
import com.librarymanagement.mapper.LoanMapper;
import com.librarymanagement.model.Book;
import com.librarymanagement.model.Loan;
import com.librarymanagement.model.Member;
import com.librarymanagement.repository.BookRepository;
import com.librarymanagement.repository.LoanRepository;
import com.librarymanagement.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("LoanService Tests")
class LoanServiceTest {

    @Mock private LoanRepository   loanRepository;
    @Mock private BookRepository   bookRepository;
    @Mock private MemberRepository memberRepository;
    @Mock private LoanMapper       loanMapper;

    @InjectMocks
    private LoanService loanService;

    private Book book;
    private Member member;
    private Loan loan;
    private LoanDTO.Request request;
    private LoanDTO.Response response;

    @BeforeEach
    void setUp() {
        book = Book.builder()
                .id(1L).title("Clean Code")
                .isbn("978-0132350884")
                .author("Robert C. Martin")
                .category(BookCategory.TECHNOLOGY)
                .available(true)
                .build();

        member = Member.builder()
                .id(1L).name("Sergio Silva")
                .email("sergio@example.com")
                .phone("3001234567")
                .build();

        loan = Loan.builder()
                .id(1L).book(book).member(member)
                .loanDate(LocalDate.now())
                .status(LoanStatus.ACTIVE)
                .build();

        request  = new LoanDTO.Request(1L, 1L);

        response = new LoanDTO.Response(
                1L, 1L, "Clean Code", "978-0132350884",
                1L, "Sergio Silva",
                LocalDate.now(), null, LoanStatus.ACTIVE
        );
    }

    // ── create ────────────────────────────────────────────────────

    @Test
    @DisplayName("Should create loan when book is available")
    void shouldCreateLoan_whenBookIsAvailable() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);
        when(loanMapper.toResponse(any(Loan.class))).thenReturn(response);

        LoanDTO.Response result = loanService.create(request);

        assertThat(result).isNotNull();
        assertThat(result.status()).isEqualTo(LoanStatus.ACTIVE);
        assertThat(book.isAvailable()).isFalse();
        verify(bookRepository).save(book);
        verify(loanRepository).save(any(Loan.class));
    }

    @Test
    @DisplayName("Should throw BookNotAvailableException when book is not available")
    void shouldThrowException_whenBookNotAvailable() {
        book.setAvailable(false);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        assertThatThrownBy(() -> loanService.create(request))
                .isInstanceOf(BookNotAvailableException.class)
                .hasMessageContaining("978-0132350884");

        verify(loanRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when book does not exist")
    void shouldThrowException_whenBookNotFound() {
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        LoanDTO.Request badRequest = new LoanDTO.Request(99L, 1L);

        assertThatThrownBy(() -> loanService.create(badRequest))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    // ── returnBook ────────────────────────────────────────────────

    @Test
    @DisplayName("Should return book and update status to RETURNED")
    void shouldReturnBook_andUpdateStatus() {
        book.setAvailable(false);
        LoanDTO.Response returnedResponse = new LoanDTO.Response(
                1L, 1L, "Clean Code", "978-0132350884",
                1L, "Sergio Silva",
                LocalDate.now(), LocalDate.now(), LoanStatus.RETURNED
        );

        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);
        when(loanMapper.toResponse(any(Loan.class))).thenReturn(returnedResponse);

        LoanDTO.Response result = loanService.returnBook(1L);

        assertThat(result.status()).isEqualTo(LoanStatus.RETURNED);
        assertThat(result.returnDate()).isNotNull();
        assertThat(book.isAvailable()).isTrue();
        verify(bookRepository).save(book);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when loan does not exist")
    void shouldThrowException_whenLoanNotFound() {
        when(loanRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> loanService.returnBook(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}