package com.librarymanagement.repository;

import com.librarymanagement.enums.LoanStatus;
import com.librarymanagement.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByMemberId(Long memberId);

    List<Loan> findByStatus(LoanStatus status);

    List<Loan> findByMemberIdAndStatus(Long memberId, LoanStatus status);
}