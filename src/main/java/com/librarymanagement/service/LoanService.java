package com.librarymanagement.service;

import com.librarymanagement.dto.LoanDTO;
import com.librarymanagement.exception.BookNotAvailableException;
import com.librarymanagement.exception.ResourceNotFoundException;
import com.librarymanagement.mapper.LoanMapper;
import com.librarymanagement.model.Book;
import com.librarymanagement.model.Loan;
import com.librarymanagement.model.Member;
import com.librarymanagement.enums.LoanStatus;
import com.librarymanagement.repository.BookRepository;
import com.librarymanagement.repository.LoanRepository;
import com.librarymanagement.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository   loanRepository;
    private final BookRepository   bookRepository;
    private final MemberRepository memberRepository;
    private final LoanMapper       loanMapper;

    @Transactional
    public LoanDTO.Response create(LoanDTO.Request request) {
        Book book = bookRepository.findById(request.bookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book", request.bookId()));

        if (!book.isAvailable()) {
            throw new BookNotAvailableException(book.getIsbn());
        }

        Member member = memberRepository.findById(request.memberId())
                .orElseThrow(() -> new ResourceNotFoundException("Member", request.memberId()));

        Loan loan = Loan.builder()
                .book(book)
                .member(member)
                .loanDate(LocalDate.now())
                .status(LoanStatus.ACTIVE)
                .build();

        book.setAvailable(false);
        bookRepository.save(book);

        return loanMapper.toResponse(loanRepository.save(loan));
    }

    @Transactional
    public LoanDTO.Response returnBook(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan", loanId));

        loan.setStatus(LoanStatus.RETURNED);
        loan.setReturnDate(LocalDate.now());

        loan.getBook().setAvailable(true);
        bookRepository.save(loan.getBook());

        return loanMapper.toResponse(loanRepository.save(loan));
    }

    public List<LoanDTO.Response> findAll() {
        return loanRepository.findAll()
                .stream().map(loanMapper::toResponse).toList();
    }

    public LoanDTO.Response findById(Long id) {
        return loanRepository.findById(id)
                .map(loanMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Loan", id));
    }

    public List<LoanDTO.Response> findByMember(Long memberId) {
        return loanRepository.findByMemberId(memberId)
                .stream().map(loanMapper::toResponse).toList();
    }
}