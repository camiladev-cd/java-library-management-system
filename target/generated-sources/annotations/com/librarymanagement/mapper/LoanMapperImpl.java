package com.librarymanagement.mapper;

import com.librarymanagement.dto.LoanDTO;
import com.librarymanagement.enums.LoanStatus;
import com.librarymanagement.model.Book;
import com.librarymanagement.model.Loan;
import com.librarymanagement.model.Member;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-03T09:03:46-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.11 (Oracle Corporation)"
)
@Component
public class LoanMapperImpl implements LoanMapper {

    @Override
    public LoanDTO.Response toResponse(Loan loan) {
        if ( loan == null ) {
            return null;
        }

        Long bookId = null;
        String bookTitle = null;
        String bookIsbn = null;
        Long memberId = null;
        String memberName = null;
        Long id = null;
        LocalDate loanDate = null;
        LocalDate returnDate = null;
        LoanStatus status = null;

        bookId = loanBookId( loan );
        bookTitle = loanBookTitle( loan );
        bookIsbn = loanBookIsbn( loan );
        memberId = loanMemberId( loan );
        memberName = loanMemberName( loan );
        id = loan.getId();
        loanDate = loan.getLoanDate();
        returnDate = loan.getReturnDate();
        status = loan.getStatus();

        LoanDTO.Response response = new LoanDTO.Response( id, bookId, bookTitle, bookIsbn, memberId, memberName, loanDate, returnDate, status );

        return response;
    }

    private Long loanBookId(Loan loan) {
        if ( loan == null ) {
            return null;
        }
        Book book = loan.getBook();
        if ( book == null ) {
            return null;
        }
        Long id = book.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String loanBookTitle(Loan loan) {
        if ( loan == null ) {
            return null;
        }
        Book book = loan.getBook();
        if ( book == null ) {
            return null;
        }
        String title = book.getTitle();
        if ( title == null ) {
            return null;
        }
        return title;
    }

    private String loanBookIsbn(Loan loan) {
        if ( loan == null ) {
            return null;
        }
        Book book = loan.getBook();
        if ( book == null ) {
            return null;
        }
        String isbn = book.getIsbn();
        if ( isbn == null ) {
            return null;
        }
        return isbn;
    }

    private Long loanMemberId(Loan loan) {
        if ( loan == null ) {
            return null;
        }
        Member member = loan.getMember();
        if ( member == null ) {
            return null;
        }
        Long id = member.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String loanMemberName(Loan loan) {
        if ( loan == null ) {
            return null;
        }
        Member member = loan.getMember();
        if ( member == null ) {
            return null;
        }
        String name = member.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
