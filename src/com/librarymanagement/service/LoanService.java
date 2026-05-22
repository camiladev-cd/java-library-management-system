package com.librarymanagement.service;

import java.util.ArrayList;
import com.librarymanagement.model.Loan;
import com.librarymanagement.model.Book;
import com.librarymanagement.model.Member;

public class LoanService {

    private ArrayList<Loan> loans;

    public LoanService() {
        this.loans = new ArrayList<>();
    }


    public void borrowBook(Book book, Member member) {

        if (!book.isAvailable()) {
            return;
        }

        book.markAsBorrowed();
        member.incrementBorrowedBooks();
        Loan newLoan = new Loan(book, member);
        loans.add(newLoan);
    }

    public void returnBook(Book book, Member member) {

        Loan foundLoan = null;

        for (Loan currentLoan : loans) {

            if (currentLoan.getBook() == book &&
                    currentLoan.getMember() == member) {

                foundLoan = currentLoan;
                break;
            }
        }

        if (foundLoan != null) {
            book.markAsReturned();
            member.decrementBorrowedBooks();
            loans.remove(foundLoan);
        }
    }

}
