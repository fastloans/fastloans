package com.app.lms.repository;

import com.app.lms.dto.LoanDto;
import com.app.lms.entity.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    @Query("SELECT l from Loan l WHERE l.userId = :userId")
    Page<Loan> getPaginatedLoanOfUser(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT new com.app.lms.dto.LoanDto(l.id,l.amount,l.interest,l.startSlot,l.endSlot,l.isPaid,l.remark,l.loanType) from Loan l WHERE l.id=:loanId")
    Optional<LoanDto> getLoanDtoById(@Param("loanId") Long loanId);
}