package com.app.lms.repository;

import com.app.lms.dto.LoanAmountMapping;
import com.app.lms.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByInstalmentId(Long instamentId);

    @Query("SELECT new com.app.lms.dto.LoanAmountMapping(p.loanId,sum(p.amount)) from Payment p where p.loanId in (:loanIds) group by p.loanId")
    List<LoanAmountMapping> getPaidAmountByLoanId(@Param("loanIds") List<Long> loanIds);

    @Query("SELECT SUM(p.amount) from Payment p")
    BigDecimal getTotalPayment();
}